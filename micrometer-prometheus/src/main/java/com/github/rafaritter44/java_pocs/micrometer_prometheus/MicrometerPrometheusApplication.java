package com.github.rafaritter44.java_pocs.micrometer_prometheus;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.net.httpserver.HttpServer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@SpringBootApplication
@RestController
public class MicrometerPrometheusApplication {
	
	private static Counter usersCounter;
	private static Counter booksCounter;

	public static void main(String[] args) {
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
		usersCounter = registry.counter("http.requests", "endpoint", "/users");
		booksCounter = registry.counter("http.requests", "endpoint", "/books");
		SpringApplication.run(MicrometerPrometheusApplication.class, args);
	}
	
	@GetMapping("/users")
	public String helloUsers() {
		usersCounter.increment();
		return "Hello, users!";
	}
	
	@GetMapping("/books")
	public String helloBooks() {
		booksCounter.increment();
		return "Hello, books!";
	}

}