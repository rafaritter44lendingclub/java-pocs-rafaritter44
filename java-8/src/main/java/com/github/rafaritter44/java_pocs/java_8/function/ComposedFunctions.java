package com.github.rafaritter44.java_pocs.java_8.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ComposedFunctions {
	
	private final List<Integer> NUMBERS = new ArrayList<>();
	private final int SIZE = 100;
	private final int MAX_LENGTH = 3;
	private int count = 0;

	public void execute() {
		randomNumbers().filter(isPositive().and(isEven()))
				.map(cut().compose(Object::toString).andThen(Integer::parseInt)).sorted()
				.forEachOrdered(add().andThen(check()).andThen(System.out::println));
	}

	private Stream<Integer> randomNumbers() {
		return IntStream.generate(new Random()::nextInt).limit(SIZE).boxed();
	}

	private Predicate<Integer> isPositive() {
		return number -> number > 0;
	}

	private Predicate<Integer> isEven() {
		return number -> number % 2 == 0;
	}

	private Function<String, String> cut() {
		return string -> cut(string).orElse(string);
	}

	private Optional<String> cut(String string) {
		try {
			return Optional.of(string.substring(string.length() - MAX_LENGTH));
		} catch (Throwable t) {
			return Optional.empty();
		}
	}

	private Consumer<Integer> add() {
		return NUMBERS::add;
	}

	private Consumer<Integer> check() {
		return number -> {
			if (!NUMBERS.get(count++).equals(number))
				throw new RuntimeException("Does not match expected number.");
		};
	}

}
