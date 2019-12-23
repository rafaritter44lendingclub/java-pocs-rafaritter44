package com.github.rafaritter44.java_pocs.micrometer_prometheus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@SpringBootApplication
public class MicrometerPrometheusApplication {
	
	public static void main(final String[] args) {
		SpringApplication.run(MicrometerPrometheusApplication.class, args);
	}
	
	@Bean
	public PrometheusMeterRegistry registry() {
		return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT); 
	}

}