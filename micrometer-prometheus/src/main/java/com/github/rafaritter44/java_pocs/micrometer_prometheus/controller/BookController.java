package com.github.rafaritter44.java_pocs.micrometer_prometheus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@RestController
@RequestMapping("/books")
public class BookController {
	
	private final Counter booksCounter;
	
	public BookController(final PrometheusMeterRegistry registry) {
		booksCounter = registry.counter("http.requests", "endpoint", "/books");
	}
	
	@GetMapping
	public String helloBooks() {
		booksCounter.increment();
		return "Hello, books!";
	}
	
}
