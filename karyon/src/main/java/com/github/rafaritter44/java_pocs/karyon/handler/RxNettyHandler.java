package com.github.rafaritter44.java_pocs.karyon.handler;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import netflix.karyon.transport.http.health.HealthCheckEndpoint;
import rx.Observable;

public class RxNettyHandler implements RequestHandler<ByteBuf, ByteBuf> {

	private final String healthCheckUri;
	private final HealthCheckEndpoint healthCheckEndpoint;

	public RxNettyHandler(String healthCheckUri, HealthCheckEndpoint healthCheckEndpoint) {
		this.healthCheckUri = healthCheckUri;
		this.healthCheckEndpoint = healthCheckEndpoint;
	}

	@Override
	public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
		String uri = request.getUri();
		if (uri.startsWith(healthCheckUri)) {
			return healthCheckEndpoint.handle(request, response);
		} else if (uri.startsWith("/ping")) {
			return response.writeStringAndFlush("pong");
		} else {
			response.setStatus(HttpResponseStatus.NOT_FOUND);
			return response.close();
		}
	}

}