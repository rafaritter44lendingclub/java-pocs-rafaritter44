package com.github.rafaritter44.java_pocs.annotation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, NoSuchMethodException {
		Class<MyAnnotatedClass> clazz = MyAnnotatedClass.class;
		Package pkg = clazz.getPackage();		
		Field field = clazz.getDeclaredField("field");
		Constructor<MyAnnotatedClass> constructor = clazz.getConstructor();
		Method method = clazz.getMethod("method", Object.class);
		Parameter param = method.getParameters()[0];
		
		Stream<AnnotatedElement> annotatedElements = Stream
				.<AnnotatedElement>builder()
				.add(clazz)
				.add(pkg)
				.add(field)
				.add(constructor)
				.add(method)
				.add(param)
				.build();
		
		annotatedElements.forEach(Main::printValueOf);
	}
	
	private static void printValueOf(AnnotatedElement annotatedElement) {
		System.out.println(annotatedElement.getAnnotation(MyAnnotation.class).value());
	}
	
}
