package com.github.rafaritter44.java_pocs.java_8.function;

import java.util.Comparator;
import java.util.Date;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.github.rafaritter44.java_pocs.java_8.model.Person;

public class Functions {
	
	private Function<String, String> greet;
	private Consumer<String> print;
	private Supplier<String> clock;
	private Stream<String> people;
	private BinaryOperator<String> group;
	private Predicate<String> isReal;
	private Comparator<String> reverseComparator;
	
	public Functions() {
		greet = (greeted) -> ("Hello " + greeted + "!");
		print = System.out::println;
		clock = () -> new Date().toString();
		people = Stream.of("Jake Gyllenhaal", "Donnie Darko", "Rafael Ritter");
		group = (person1, person2) -> (person1 + ", " + person2);
		isReal = name -> !name.equals("Donnie Darko");
		reverseComparator = (person1, person2) -> person2.compareTo(person1);
	}
	
	public void execute() {
		print.accept(greet.apply(people
				.filter(isReal)
				.map(Person::new)
				.map(Person::getFirstName)
				.sorted(reverseComparator)
				.reduce(group)
				.orElse("nobody")) + "\n" + clock.get());
	}
	
}
