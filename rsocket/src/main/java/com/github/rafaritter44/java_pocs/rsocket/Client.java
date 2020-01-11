package com.github.rafaritter44.java_pocs.rsocket;

import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;

public class Client {
	
	public Client request(String message) {
		RSocketFactory.connect()
				.transport(TcpClientTransport.create(Server.IP, Server.PORT))
				.start()
				.block()
				.requestResponse(DefaultPayload.create(message))
				.subscribe(response -> {
					print("DATA => " + response.getDataUtf8());
					print("METADATA => " + response.getMetadataUtf8());
				});
		return this;
	}
	
	public Client fireAndForget(String message) {
		RSocketFactory.connect()
				.transport(TcpClientTransport.create(Server.IP, Server.PORT))
				.start()
				.flatMap(rsocket -> rsocket.fireAndForget(DefaultPayload.create(message)))
				.doOnError(e -> System.out.println("ERROR: " + e.getMessage()))
				.block();
		return this;
	}
	
	private void print(String message) {
		System.out.println("[CLIENT] ".concat(message));
	}
	
}
