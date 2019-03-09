package com.github.rafaritter44.java_pocs.lettuce;

import java.util.concurrent.TimeUnit;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.reactive.RedisReactiveCommands;

public class ReactiveLettuce {
	
	public static void main(String[] args) {
		RedisClient redisClient = RedisClient.create("redis://password@localhost:6379/0");
		RedisReactiveCommands<String, String> commands = redisClient.connect().reactive();
		commands.sadd("reactive-key", "one", "two", "three")
				.thenMany(commands.smembers("reactive-key"))
				.doOnNext(System.out::println)
				.subscribe();
		sleep();
	}
	
	private static void sleep() {
		try {
			TimeUnit.SECONDS.sleep(5L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
