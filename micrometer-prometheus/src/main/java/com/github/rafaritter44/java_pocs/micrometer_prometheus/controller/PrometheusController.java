package com.github.rafaritter44.java_pocs.micrometer_prometheus.controller;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@RestController
@RequestMapping("/prometheus")
public class PrometheusController {
	
	private final PrometheusMeterRegistry registry;
	
	public PrometheusController(final PrometheusMeterRegistry registry) {
		this.registry = registry;
	}
	
	@PostConstruct
	public void bindMetricsToRegistry() {
		new JvmMemoryMetrics().bindTo(registry);
	}
	
	@GetMapping
	public StreamingResponseBody scrape() {
		return outputStream -> outputStream.write(registry.scrape().getBytes());
	}

}
