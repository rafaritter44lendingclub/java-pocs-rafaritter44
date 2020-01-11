package com.github.rafaritter44.java_pocs.powermock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PowerMock.class)
public class PowerMockTest {
	
	private PowerMock powerMock;
	
	@Before
	public void setUp() {
		powerMock = new PowerMock();
	}
	
	@Test
	public void test() {
		assertEquals("Hello, World!", powerMock.instanceMethod());
		
		mockStatic(PowerMock.class);
		when(PowerMock.classMethod()).thenReturn("Hello, Mock!");

		assertEquals("Hello, Mock!", powerMock.instanceMethod());
	}
	
}
