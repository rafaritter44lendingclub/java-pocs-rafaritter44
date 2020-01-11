package com.github.rafaritter44.java_pocs.mockito;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class SpyTest {
	
	@Before
	public void setUp() {
		System.setOut(spy(System.out));
	}
	
	@Test
	public void shouldPrintToStdOut() {
		verify(System.out, never()).println(anyString());
		System.out.println("Hello");
		verify(System.out).println("Hello");
	}
	
}
