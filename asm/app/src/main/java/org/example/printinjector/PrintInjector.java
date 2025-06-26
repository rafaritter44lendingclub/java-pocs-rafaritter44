package org.example.printinjector;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PrintInjector {

    public static void main(String[] args) throws Exception {
        String className = "org/example/printinjector/Greeter";
        String classFile = "app/build/classes/java/main/" + className + ".class";

        // 1. Load the original class bytes
        byte[] originalClass = Files.readAllBytes(Paths.get(classFile));

        // 2. Modify it with ASM
        ClassReader cr = new ClassReader(originalClass);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor cv = new PrintInjectorClassVisitor(Opcodes.ASM9, cw);
        cr.accept(cv, 0);

        byte[] modifiedClass = cw.toByteArray();

        // 3. Write modified class to file (for inspection)
        File outFile = new File("GreeterModified.class");
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            fos.write(modifiedClass);
        }

        // 4. Load and run the modified class
        CustomClassLoader loader = new CustomClassLoader();
        Class<?> modifiedGreeter = loader.defineClass(null, modifiedClass);
        Object instance = modifiedGreeter.getDeclaredConstructor().newInstance();
        Method greet = modifiedGreeter.getMethod("greet");
        greet.invoke(instance);
    }

    // ClassVisitor that wraps the greet() method
    static class PrintInjectorClassVisitor extends ClassVisitor {
        public PrintInjectorClassVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            if (name.equals("greet") && desc.equals("()V")) {
                return new PrintAdviceAdapter(api, mv, access, name, desc);
            }
            return mv;
        }
    }

    // AdviceAdapter that injects println at method entry
    static class PrintAdviceAdapter extends AdviceAdapter {
        protected PrintAdviceAdapter(int api, MethodVisitor mv, int access, String name, String desc) {
            super(api, mv, access, name, desc);
        }

        @Override
        protected void onMethodEnter() {
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Injected by ASM.");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                    "(Ljava/lang/String;)V", false);
        }
    }

    // Simple class loader to load from byte[]
    static class CustomClassLoader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] b) {
            return super.defineClass(name, b, 0, b.length);
        }
    }
}