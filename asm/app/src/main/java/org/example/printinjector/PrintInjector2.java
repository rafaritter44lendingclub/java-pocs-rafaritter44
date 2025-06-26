package org.example.printinjector;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.objectweb.asm.Opcodes.*;

public class PrintInjector2 {
    public static void main(String[] args) throws IOException, java.net.URISyntaxException {
        // Load the compiled Greeter.class bytecode
        Path classFile = Path.of(Greeter.class.getResource("Greeter.class").toURI());
        byte[] classBytes = Files.readAllBytes(classFile);

        ClassReader cr = new ClassReader(classBytes);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassVisitor cv = new ClassVisitor(ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

                // Target the greet method
                if (name.equals("greet")) {
                    return new MethodVisitor(ASM9, mv) {
                        @Override
                        public void visitCode() {
                            super.visitCode();
                            // Inject: System.out.println("Injected by ASM.");
                            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            mv.visitLdcInsn("Injected by ASM.");
                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                                               "(Ljava/lang/String;)V", false);
                        }
                    };
                }
                return mv;
            }
        };

        cr.accept(cv, 0);
        byte[] modifiedClass = cw.toByteArray();

        // Save modified class
        try (FileOutputStream fos = new FileOutputStream(classFile.toFile())) {
            fos.write(modifiedClass);
        }

        System.out.println("Modified Greeter.class written.");
    }
}