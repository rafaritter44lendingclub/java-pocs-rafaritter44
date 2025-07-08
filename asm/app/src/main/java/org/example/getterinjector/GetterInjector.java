package org.example.getterinjector;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.objectweb.asm.Opcodes.*;

public class GetterInjector {
    public static void injectGetter() throws IOException, java.net.URISyntaxException {
        Path classFile = Path.of(Person.class.getResource("Person.class").toURI());
        byte[] classBytes = Files.readAllBytes(classFile);

        ClassReader cr = new ClassReader(classBytes);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassVisitor cv = new ClassVisitor(ASM9, cw) {
            private String className;

            @Override
            public void visit(int version, int access, String name, String signature,
                              String superName, String[] interfaces) {
                className = name;
                super.visit(version, access, name, signature, superName, interfaces);
            }

            @Override
            public void visitEnd() {
                MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "getName", "()Ljava/lang/String;", null, null);
                mv.visitCode();

                mv.visitVarInsn(ALOAD, 0); // this
                mv.visitFieldInsn(GETFIELD, className, "name", "Ljava/lang/String;");
                mv.visitInsn(ARETURN);

                mv.visitMaxs(0, 0); // Ignored with COMPUTE_MAXS
                mv.visitEnd();

                super.visitEnd();
            }
        };

        cr.accept(cv, 0);
        byte[] modifiedClass = cw.toByteArray();

        try (FileOutputStream fos = new FileOutputStream(classFile.toFile())) {
            fos.write(modifiedClass);
        }
    }
}