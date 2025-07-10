package org.example.mockinjector;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

class MockInjectorTest {
    @Test
    void test() throws Exception {
        MockInjector.mockAddMethod();

        Path path = Path.of("build/classes/java/main/");
        URL classDir = path.toUri().toURL();
        try (URLClassLoader loader = new URLClassLoader(new URL[]{classDir}, null)) {
            Class<?> clazz = loader.loadClass("org.example.mockinjector.Calculator");
            Object instance = clazz.getDeclaredConstructor().newInstance();

            Method method = clazz.getMethod("add", int.class, int.class);
            int result = (int) method.invoke(instance, 1, 2);
            System.out.println("Mocked add result: " + result);
        }
    }
}