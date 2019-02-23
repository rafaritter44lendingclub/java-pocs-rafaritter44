package com.github.rafaritter44.java_pocs.rx;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.github.rafaritter44.java_pocs.rx.entity.Person;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class Rx {
	
	public static void main(String[] args) {
		
		Flowable<String> reactiveProgrammers = Flowable.fromCallable(Rx::databaseCall)
				.observeOn(Schedulers.computation())
				.flatMap(Flowable::fromIterable)
				.filter(person -> person.age() >= 18)
				.map(Person::firstName)
				.subscribeOn(Schedulers.io())
				.delay(3L, TimeUnit.SECONDS);
		
		System.out.println("Nothing happened yet!");
		
		reactiveProgrammers.subscribe(reactiveProgrammer -> System.out.println("Reactive Programmer: " + reactiveProgrammer));
		
		IntStream.range(1, Integer.MAX_VALUE)
				.forEach(Rx::waitAndPrint);
		
	}
	
	private static List<Person> databaseCall() {
		return Arrays.asList(
				Person.build()
						.withName("Erik Meijer")
						.withAge(55),
				Person.build()
						.withName("Rafael Ritter")
						.withAge(22),
				Person.build()
						.withName("John Doe")
						.withAge(10));
	}
	
	private static void waitAndPrint(Integer number) {
		try {
			TimeUnit.SECONDS.sleep(1L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Count: " + number + "s");
	}
	
}
