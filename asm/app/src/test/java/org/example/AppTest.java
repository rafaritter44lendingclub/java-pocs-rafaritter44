package org.example;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import org.example.printinjector.PrintInjector2;

class AppTest {
    @Test void test() throws Exception {
        PrintInjector2.main(new String[]{});
        
        Path path = Path.of("build/classes/java/main/");
        URL classDir = path.toUri().toURL();
        try (URLClassLoader loader = new URLClassLoader(new URL[]{classDir}, null)) { // null = no parent classloader
            Class<?> greeterClass = loader.loadClass("org.example.printinjector.Greeter");

            Object greeter = greeterClass.getDeclaredConstructor().newInstance();
            Method greet = greeterClass.getMethod("greet");
            greet.invoke(greeter);
        }
    }
}