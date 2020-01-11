package com.github.rafaritter44.java_pocs.annotation;

@MyAnnotation("TYPE")
public class MyAnnotatedClass {
	
	@MyAnnotation("FIELD")
	private Object field;
	
	@MyAnnotation("CONSTRUCTOR")
	public MyAnnotatedClass() {}
	
	@MyAnnotation("METHOD")
	public void method(@MyAnnotation("PARAMETER") Object param) {}
	
}
