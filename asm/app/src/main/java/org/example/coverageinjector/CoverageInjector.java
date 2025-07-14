package org.example.coverageinjector;

import org.objectweb.asm.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.objectweb.asm.Opcodes.*;

public class CoverageInjector {
    public static void injectCoverage() throws IOException, java.net.URISyntaxException {
        Path classFile = Path.of(Printer.class.getResource("Printer.class").toURI());
        byte[] classBytes = Files.readAllBytes(classFile);

        ClassReader cr = new ClassReader(classBytes);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassVisitor cv = new ClassVisitor(ASM9, cw) {
            private String className;
            private int methodCount = 0;

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                this.className = name;
                super.visit(version, access, name, signature, superName, interfaces);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

                // Skip constructors and static initializers.
                if (name.equals("<init>") || name.equals("<clinit>")) {
                    return mv;
                }

                final int id = methodCount++;

                return new MethodVisitor(ASM9, mv) {
                    @Override
                    public void visitCode() {
                        super.visitCode();

                        // Inject: __coverage[id] = true;
                        mv.visitFieldInsn(GETSTATIC, className, "__coverage", "[Z");
                        mv.visitIntInsn(BIPUSH, id);
                        mv.visitInsn(ICONST_1);
                        mv.visitInsn(BASTORE);
                    }
                };
            }

            @Override
            public void visitEnd() {
                // Inject: static boolean[] __coverage;
                FieldVisitor fv = super.visitField(ACC_PUBLIC + ACC_STATIC, "__coverage", "[Z", null, null);
                fv.visitEnd();

                // Inject: static { __coverage = new boolean[methodCount]; }
                MethodVisitor mv = super.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
                mv.visitCode();
                mv.visitIntInsn(BIPUSH, methodCount);
                mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
                mv.visitFieldInsn(PUTSTATIC, className, "__coverage", "[Z");
                mv.visitInsn(RETURN);
                mv.visitMaxs(0, 0); // Ignored with COMPUTE_MAXS.
                mv.visitEnd();

                super.visitEnd();
            }
        };

        cr.accept(cv, 0);
        byte[] modifiedClass = cw.toByteArray();

        Path destinationRoot = Path.of("build", "asm-out");
        String relativePath = Printer.class.getName().replace('.', '/') + ".class";
        Path destinationPath = destinationRoot.resolve(relativePath);

        Files.createDirectories(destinationPath.getParent());

        Files.write(destinationPath, modifiedClass);
    }
}