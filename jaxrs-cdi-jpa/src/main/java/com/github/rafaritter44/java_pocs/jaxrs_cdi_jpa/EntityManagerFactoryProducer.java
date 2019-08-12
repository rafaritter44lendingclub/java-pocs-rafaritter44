package com.github.rafaritter44.java_pocs.jaxrs_cdi_jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProducer {
	
	@Produces
	@ApplicationScoped
	public EntityManagerFactory create() {
		return Persistence.createEntityManagerFactory("jaxrs-cdi-jpa-PU");
	}
	
	public void destroy(@Disposes EntityManagerFactory factory) {
		factory.close();
	}
	
}
