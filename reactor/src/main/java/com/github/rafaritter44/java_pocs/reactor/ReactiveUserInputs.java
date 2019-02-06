package com.github.rafaritter44.java_pocs.reactor;

import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactiveUserInputs {
	
	private static Scanner userInputs;
	
	static {
		userInputs = new Scanner(System.in);
	}

	public static void main(String[] args) throws InterruptedException {
		
		Mono.fromSupplier(userInputs::nextLine)
				.repeat()
				.subscribeOn(Schedulers.elastic())
				.subscribe(input -> System.out.println("From user: " + input));
		
		Flux.interval(Duration.ofSeconds(1L))
				.subscribe(tick -> System.out.println("Count: " + tick + "s"));

		TimeUnit.DAYS.sleep(Long.MAX_VALUE);
		
	}

}
