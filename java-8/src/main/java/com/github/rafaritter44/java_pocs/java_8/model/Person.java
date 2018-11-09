package com.github.rafaritter44.java_pocs.java_8.model;

public class Person {
	
	private String firstName;
	private String surname;
	
	public Person(String fullName) {
		String[] names = fullName.split(" ");
		firstName = names[0];
		surname = names[1];
	}
	
	public String getFirstName() { return firstName; }
	public String getSurname() { return surname; }

}
