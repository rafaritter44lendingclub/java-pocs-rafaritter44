package com.github.rafaritter44.java_pocs.jeromq.consumer;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Consumer {
	
	public static void main(String[] args) {
		try (ZContext context = new ZContext()) {
			ZMQ.Socket socket = context.createSocket(SocketType.PULL);
			socket.bind("tcp://*:5555");
			while (true) {
				System.out.println(socket.recvStr());
			}
		}
	}
	
}
