package com.github.rafaritter44.java_pocs.jaxrs_cdi_jpa;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerProducer {
	
	@Inject
	transient EntityManagerFactory entityManagerFactory;
	
	@Produces
	@RequestScoped
	public EntityManager create() {
		return entityManagerFactory.createEntityManager();
	}
	
	public void destroy(@Disposes EntityManager entityManager) {
		entityManager.close();
	}
	
}
