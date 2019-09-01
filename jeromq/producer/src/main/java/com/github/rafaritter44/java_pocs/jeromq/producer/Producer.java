package com.github.rafaritter44.java_pocs.jeromq.producer;

import java.util.concurrent.TimeUnit;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Producer {
	
	private static final int ADDRESS = 0;
	
	public static void main(String[] args) {
		try (ZContext context = new ZContext()) {
			ZMQ.Socket socket = context.createSocket(SocketType.PUSH);
			socket.connect(String.format("tcp://%s:5555", args.length == 0 ? "localhost" : args[ADDRESS]));
			while (true) {
				socket.send("Hello, World!");
				TimeUnit.SECONDS.sleep(2L);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
