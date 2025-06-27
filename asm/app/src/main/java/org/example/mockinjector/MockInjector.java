package org.example.mockinjector;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.objectweb.asm.Opcodes.*;

public class MockInjector {
    public static void mockAddMethod() throws IOException, java.net.URISyntaxException {
        Path classFile = Path.of(Calculator.class.getResource("Calculator.class").toURI());
        byte[] classBytes = Files.readAllBytes(classFile);

        ClassReader cr = new ClassReader(classBytes);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        ClassVisitor cv = new ClassVisitor(ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

                if (name.equals("add") && descriptor.equals("(II)I")) {
                    // Replace method body
                    return new MethodVisitor(ASM9, mv) {
                        @Override
                        public void visitCode() {
                            mv.visitCode();
                            mv.visitLdcInsn(42); // Push constant int 42
                            mv.visitInsn(IRETURN);
                            mv.visitMaxs(1, 3); // Stack size, local variables (ignored with COMPUTE_MAXS)
                            mv.visitEnd();
                        }
                    };
                }

                return mv;
            }
        };

        cr.accept(cv, 0);
        byte[] modifiedClass = cw.toByteArray();

        try (FileOutputStream fos = new FileOutputStream(classFile.toFile())) {
            fos.write(modifiedClass);
        }
    }
}