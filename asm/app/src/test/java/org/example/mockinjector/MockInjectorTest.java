package org.example.mockinjector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

class MockInjectorTest {
    @Test
    void test() throws Exception {
        MockInjector.injectMock();

        URL classDir = Path.of("build", "asm-out").toUri().toURL();
        try (URLClassLoader loader = new URLClassLoader(new URL[]{classDir}, null)) {
            Class<?> calculatorClass = loader.loadClass("org.example.mockinjector.Calculator");

            Object calculator = calculatorClass.getDeclaredConstructor().newInstance();
            Method add = calculatorClass.getMethod("add", int.class, int.class);
            var result = add.invoke(calculator, 1, 2);

            assertEquals(7, result);
        }
    }
}