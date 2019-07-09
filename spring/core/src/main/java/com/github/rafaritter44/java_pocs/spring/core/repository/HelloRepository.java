package com.github.rafaritter44.java_pocs.spring.core.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class HelloRepository {
	
	public List<String> getAll() {
		return Arrays.asList("Rafael Ritter", "Rod Johnson", "John Doe");
	}
	
}
