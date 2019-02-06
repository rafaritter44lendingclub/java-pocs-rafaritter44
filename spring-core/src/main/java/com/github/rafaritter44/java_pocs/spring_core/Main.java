package com.github.rafaritter44.java_pocs.spring_core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.rafaritter44.java_pocs.spring_core.config.AppConfig;
import com.github.rafaritter44.java_pocs.spring_core.service.GoodbyeService;
import com.github.rafaritter44.java_pocs.spring_core.service.HelloService;

public class Main {
	
	public static void main(String[] args) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
		HelloService helloService = appContext.getBean(HelloService.class);
		GoodbyeService goodbyeService = appContext.getBean(GoodbyeService.class);
		System.out.println(helloService.helloToAll());
		System.out.println(goodbyeService.goodbye());
		((AnnotationConfigApplicationContext) appContext).close();
	}
	
}
