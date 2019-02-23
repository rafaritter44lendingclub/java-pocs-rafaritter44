package com.github.rafaritter44.java_pocs.rx.entity;

public class Person {
	
	private static final int FIRST = 0;
	private static final int LAST = 1;
	
	private String firstName;
	private String lastName;
	private Integer age;
	
	private Person() {}
	
	public static Person build() {
		return new Person();
	}
	
	public Person withName(String fullName) {
		String[] names = fullName.split(" ");
		firstName = names[FIRST];
		lastName = names[LAST];
		return this;
	}
	
	public Person withAge(Integer age) {
		this.age = age;
		return this;
	}
	
	public String firstName() { return firstName; }
	public String lastName() { return lastName; }
	public Integer age() { return age; }

}
