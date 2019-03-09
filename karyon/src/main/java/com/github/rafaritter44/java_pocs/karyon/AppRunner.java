package com.github.rafaritter44.java_pocs.karyon;

import com.github.rafaritter44.java_pocs.karyon.handler.RxNettyHandler;
import com.github.rafaritter44.java_pocs.karyon.health.HealthCheckResource;

import netflix.adminresources.resources.KaryonWebAdminModule;
import netflix.karyon.Karyon;
import netflix.karyon.KaryonBootstrapModule;
import netflix.karyon.ShutdownModule;
import netflix.karyon.archaius.ArchaiusBootstrapModule;
import netflix.karyon.health.HealthCheckHandler;
import netflix.karyon.servo.KaryonServoModule;
import netflix.karyon.transport.http.health.HealthCheckEndpoint;

@SuppressWarnings("deprecation")
public class AppRunner {

	private static final int PORT = 8888;
	
	public static void main(String[] args) {
		System.setProperty("java.awt.headless","true");
		System.setProperty("archaius.deployment.environment","dev");
		
		HealthCheckHandler healthCheckHandler = new HealthCheckResource();
        Karyon.forRequestHandler(PORT,
                new RxNettyHandler("/healthcheck",
                        new HealthCheckEndpoint(healthCheckHandler)),
                new KaryonBootstrapModule(healthCheckHandler),
                new ArchaiusBootstrapModule("karyon-netflix-oss"),
                Karyon.toBootstrapModule(KaryonWebAdminModule.class),
                ShutdownModule.asBootstrapModule(),
                KaryonServoModule.asBootstrapModule()
        ).startAndWaitTillShutdown();
	}
	
}
