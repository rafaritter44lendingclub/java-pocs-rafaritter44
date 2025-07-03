package org.example.printinjector;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

class PrintInjectorTest {
    @Test void test() throws Exception {
        PrintInjector.injectPrint();

        Path path = Path.of("build/asm-out");
        URL classDir = path.toUri().toURL();
        try (URLClassLoader loader = new URLClassLoader(new URL[]{classDir}, Greeter.class.getClassLoader())) {
            Class<?> greeterClass = loader.loadClass("org.example.printinjector.GreeterImpl");

            Greeter greeter = (Greeter) greeterClass.getDeclaredConstructor().newInstance();
            greeter.greet();
        }
    }
}