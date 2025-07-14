package org.example.coverageinjector;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Arrays;

class CoverageInjectorTest {
    @Test
    void test() throws Exception {
        CoverageInjector.injectCoverage();

        URL classDir = Path.of("build", "asm-out").toUri().toURL();
        try (URLClassLoader loader = new URLClassLoader(new URL[]{classDir}, null)) {
            Class<?> printerClass = loader.loadClass("org.example.coverageinjector.Printer");

            Object printer = printerClass.getDeclaredConstructor().newInstance();

            Method methodA = printerClass.getMethod("a");
            Method methodB = printerClass.getMethod("b");

            methodA.invoke(printer);
            // methodB not invoked.

            Field coverage = printerClass.getField("__coverage");
            boolean[] flags = (boolean[]) coverage.get(null);
            System.out.println("Coverage: " + Arrays.toString(flags));
        }
    }
}