package com.github.rafaritter44.java_pocs.micrometer_prometheus;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class JvmMemorySample {
	
	public static void main(String[] args) throws InterruptedException {
		PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
		try {
		    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		    server.createContext("/prometheus", httpExchange -> {
		        String response = registry.scrape();
		        httpExchange.sendResponseHeaders(200, response.getBytes().length);
		        try (OutputStream os = httpExchange.getResponseBody()) {
		            os.write(response.getBytes());
		        }
		    });
		    new Thread(server::start).start();
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
		new JvmMemoryMetrics().bindTo(registry);
		Thread.currentThread().join();
	}
	
}
