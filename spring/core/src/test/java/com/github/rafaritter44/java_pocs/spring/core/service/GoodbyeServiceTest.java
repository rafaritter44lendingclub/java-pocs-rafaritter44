package com.github.rafaritter44.java_pocs.spring.core.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class GoodbyeServiceTest {
	
	private GoodbyeService goodbyeService;
	
	@Before
	public void setUp() {
		goodbyeService = new GoodbyeService();
		goodbyeService.setGoodbyeMessage("Mock Message");
	}
	
	@Test
	public void testGoodbye() {
		assertEquals("Mock Message", goodbyeService.goodbye());
	}
	
}
