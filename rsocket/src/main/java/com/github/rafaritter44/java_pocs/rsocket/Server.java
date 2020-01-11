package com.github.rafaritter44.java_pocs.rsocket;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Mono;

public class Server extends AbstractRSocket {
	
	public static final String IP = "localhost";
	public static final int PORT = 5001;
	
	public Server() {
		RSocketFactory.receive()
			.acceptor((setupPayload, reactiveSocket) -> Mono.just(this))
			.transport(TcpServerTransport.create(IP, PORT))
			.start()
			.subscribe();
	}
	
	@Override
	public Mono<Payload> requestResponse(Payload payload) {
		return Mono.just("From client: ".concat(payload.getDataUtf8()))
				.map(response -> DefaultPayload.create(response, "metadata from server"));
	}
	
	@Override
	public Mono<Void> fireAndForget(Payload payload) {
		print("From client: ".concat(payload.getDataUtf8()));
		return Mono.empty();
	}
	
	private void print(String message) {
		System.out.println("[SERVER] ".concat(message));
	}
	
}
