package com.github.rafaritter44.java_pocs.reactor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ReactiveTest {
	
	@Test
	public void test() {
		Flux<Integer> numbers = Flux.just(1, 2, 3)
				.concatWith(Mono.error(() -> new RuntimeException()));
		
		StepVerifier.create(increment(numbers))
				.expectNext(2)
				.expectNext(3).as("Testing if number 2 was incremented.")
				.assertNext(number -> assertEquals(4, (int) number))
				.verifyError(RuntimeException.class);
	}
	
	private Flux<Integer> increment(Flux<Integer> numbers) {
		return numbers.map(number -> number + 1);
	}
	
}
