package com.github.rafaritter44.java_pocs.powermock;

public class PowerMock {
	
	public static String classMethod() {
		return "Hello, World!";
	}
	
	public String instanceMethod() {
		return PowerMock.classMethod();
	}
	
}
