package com.github.rafaritter44.java_pocs.spring.core.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rafaritter44.java_pocs.spring.core.repository.HelloRepository;

@Service
public class HelloService {
	
	@Autowired
	private HelloRepository helloRepository;
	
	public String helloToAll() {
		return "Hello to: " + helloRepository.getAll().stream().collect(Collectors.joining(", "));
	}

}
