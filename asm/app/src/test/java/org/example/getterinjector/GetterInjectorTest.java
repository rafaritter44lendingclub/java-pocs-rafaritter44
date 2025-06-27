package org.example.getterinjector;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

class GetterInjectorTest {
    @Test
    void test() throws Exception {
        GetterInjector.injectGetter();

        Path path = Path.of("build/classes/java/main/");
        URL classDir = path.toUri().toURL();
        try (URLClassLoader loader = new URLClassLoader(new URL[]{classDir}, null)) {
            Class<?> personClass = loader.loadClass("org.example.getterinjector.Person");
            Object person = personClass.getDeclaredConstructor(String.class).newInstance("John Doe");

            Method getName = personClass.getMethod("getName");
            String name = (String) getName.invoke(person);
            System.out.println("Getter returned: " + name);
        }
    }
}