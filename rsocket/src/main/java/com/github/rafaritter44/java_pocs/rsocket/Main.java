package com.github.rafaritter44.java_pocs.rsocket;

import java.util.concurrent.TimeUnit;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		new Server();
		new Client()
				.fireAndForget("message")
				.fireAndForget("test")
				.request("testing")
				.request("hello");
		TimeUnit.SECONDS.sleep(1L);
	}
	
}
