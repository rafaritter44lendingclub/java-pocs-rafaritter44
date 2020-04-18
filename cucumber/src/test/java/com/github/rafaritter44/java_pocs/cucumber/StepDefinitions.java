package com.github.rafaritter44.java_pocs.cucumber;

import static org.junit.Assert.assertEquals;

import io.cucumber.java8.En;

public class StepDefinitions implements En {
	
	private static class IsItFriday {
		public static String isItFriday(final String today) {
			return "Friday".equals(today) ? "TGIF" : "Nope";
		}
	}
	
	private String today;
	private String actualAnswer;
	
	public StepDefinitions() {
		Given("today is {string}", (final String today) -> {
		    this.today = today;
		});
		When("I ask whether it's Friday yet", () -> {
		    actualAnswer = IsItFriday.isItFriday(today);
		});
		Then("I should be told {string}", (final String expectedAnswer) -> {
		    assertEquals(expectedAnswer, actualAnswer);
		});
	}
	
}
