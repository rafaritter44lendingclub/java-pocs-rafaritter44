package com.github.rafaritter44.java_pocs.mockito;

import static java.util.stream.Collectors.joining;

import com.github.rafaritter44.java_pocs.mockito.dao.PersonDAO;

public class HelloWorld {
	
	private PersonDAO personDAO;
	
	public HelloWorld(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}
	
	public String sayHelloToEveryone() {
		String everyone = getEveryone();
		return everyone.isEmpty()? "No one to say hello to. :(":
			"Hello " + everyone + "!";
	}
	
	private String getEveryone() {
		return personDAO.getAll().stream().collect(joining(", "));
	}
	
}
