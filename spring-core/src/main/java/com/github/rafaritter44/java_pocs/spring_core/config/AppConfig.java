package com.github.rafaritter44.java_pocs.spring_core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.github.rafaritter44.java_pocs.spring_core.service.GoodbyeService;

@Configuration
@ComponentScan(basePackages = "com.github.rafaritter44.java_pocs.spring_core")
public class AppConfig {
	
	@Bean
	public GoodbyeService goodbyeService() {
		GoodbyeService goodbyeService = new GoodbyeService();
		goodbyeService.setGoodbyeMessage("Goodbye, World!");
		return goodbyeService;
	}
	
}
