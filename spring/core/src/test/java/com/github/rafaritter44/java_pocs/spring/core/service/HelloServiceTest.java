package com.github.rafaritter44.java_pocs.spring.core.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.rafaritter44.java_pocs.spring.core.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class HelloServiceTest {
	
	@Autowired
	private HelloService helloService;
	
	@Test
	public void testHelloToAll() {
		assertNotNull(helloService);
		assertNotNull(helloService.helloToAll());
	}
	
}
