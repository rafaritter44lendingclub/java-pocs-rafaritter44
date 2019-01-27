package com.github.rafaritter44.java_pocs.mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.rafaritter44.java_pocs.mockito.dao.PersonDAO;

public class HelloWorldTest {

	private HelloWorld helloWorld;
	private PersonDAO personDAO;
	
	@Before
	public void setUp() {
		personDAO = mock(PersonDAO.class);
		List<String> people = new ArrayList<>();
		doAnswer(invocation -> {
			people.add(invocation.getArgumentAt(0, String.class));
			return null;
		}).when(personDAO).add(notNull(String.class));
		when(personDAO.getAll()).thenReturn(people);
		helloWorld = new HelloWorld(personDAO);
	}
	
	@Test
	public void sayHelloToEveryone() {
		assertEquals("No one to say hello to. :(", helloWorld.sayHelloToEveryone());
		personDAO.add("Rafael Ritter");
		personDAO.add("Tarzan");
		personDAO.add("Socrates");
		assertEquals("Hello Rafael Ritter, Tarzan, Socrates!", helloWorld.sayHelloToEveryone());
		verify(personDAO, times(3)).add(anyString());
	}

}
