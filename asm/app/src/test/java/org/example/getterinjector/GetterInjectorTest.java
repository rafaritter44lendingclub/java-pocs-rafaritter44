package org.example.getterinjector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

class GetterInjectorTest {
    @Test
    void test() throws Exception {
        GetterInjector.injectGetter();

        URL classDir = Path.of("build", "asm-out").toUri().toURL();
        try (URLClassLoader loader = new URLClassLoader(new URL[]{classDir}, null)) {
            Class<?> personClass = loader.loadClass("org.example.getterinjector.Person");

            String name = "Raskólnikov";
            Object person = personClass.getDeclaredConstructor(String.class).newInstance(name);
            Method getName = personClass.getMethod("getName");

            assertEquals(name, getName.invoke(person));
        }
    }
}