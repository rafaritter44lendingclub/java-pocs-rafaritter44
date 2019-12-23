package com.github.rafaritter44.java_pocs.micrometer_prometheus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final Counter usersCounter;
	
	public UserController(final PrometheusMeterRegistry registry) {
		usersCounter = registry.counter("http.requests", "endpoint", "/users");
	}
	
	@GetMapping
	public String helloUsers() {
		usersCounter.increment();
		return "Hello, users!";
	}

}
