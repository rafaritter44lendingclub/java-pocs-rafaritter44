package org.example.coverageinjector;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.objectweb.asm.Opcodes.*;

public class CoverageInjector {
    public static void injectCoverage() throws Exception {
        Path classFile = Path.of(Printer.class.getResource("Printer.class").toURI());
        byte[] original = Files.readAllBytes(classFile);

        ClassReader cr = new ClassReader(original);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        ClassVisitor cv = new ClassVisitor(ASM9, cw) {
            private String className;
            private int methodCount = 0;

            @Override
            public void visit(int version, int access, String name, String signature,
                              String superName, String[] interfaces) {
                this.className = name;
                super.visit(version, access, name, signature, superName, interfaces);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

                // Skip constructors and static initializers
                if (name.equals("<init>") || name.equals("<clinit>")) return mv;

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
                // Add static boolean[] __coverage = new boolean[methodCount];
                FieldVisitor fv = super.visitField(ACC_PUBLIC + ACC_STATIC, "__coverage", "[Z", null, null);
                fv.visitEnd();

                // Inject static initializer to allocate the array
                MethodVisitor mv = super.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
                mv.visitCode();
                mv.visitIntInsn(BIPUSH, methodCount);
                mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
                mv.visitFieldInsn(PUTSTATIC, className, "__coverage", "[Z");
                mv.visitInsn(RETURN);
                mv.visitMaxs(1, 0);
                mv.visitEnd();

                super.visitEnd();
            }
        };

        cr.accept(cv, 0);
        byte[] modified = cw.toByteArray();

        try (FileOutputStream fos = new FileOutputStream(classFile.toFile())) {
            fos.write(modified);
        }
    }
}