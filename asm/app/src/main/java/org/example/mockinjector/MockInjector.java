package org.example.mockinjector;

import org.objectweb.asm.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.objectweb.asm.Opcodes.*;

public class MockInjector {
    public static void injectMock() throws IOException, java.net.URISyntaxException {
        Path classFile = Path.of(Calculator.class.getResource("Calculator.class").toURI());
        byte[] classBytes = Files.readAllBytes(classFile);

        ClassReader cr = new ClassReader(classBytes);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassVisitor cv = new ClassVisitor(ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (name.equals("add") && descriptor.equals("(II)I")) {
                    return new MethodVisitor(ASM9, mv) {
                        @Override
                        public void visitCode() {
                            mv.visitCode();
                            mv.visitLdcInsn(7);
                            mv.visitInsn(IRETURN);
                            mv.visitMaxs(0, 0); // Ignored with COMPUTE_MAXS.
                            mv.visitEnd();
                        }
                    };
                } else {
                    return mv;
                }
            }
        };

        cr.accept(cv, 0);
        byte[] modifiedClass = cw.toByteArray();

        Path destinationRoot = Path.of("build", "asm-out");
        String relativePath = Calculator.class.getName().replace('.', '/') + ".class";
        Path destinationPath = destinationRoot.resolve(relativePath);

        Files.createDirectories(destinationPath.getParent());

        Files.write(destinationPath, modifiedClass);
    }
}