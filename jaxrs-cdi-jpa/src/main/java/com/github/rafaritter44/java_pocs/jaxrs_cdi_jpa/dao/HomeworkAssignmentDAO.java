package com.github.rafaritter44.java_pocs.jaxrs_cdi_jpa.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import com.github.rafaritter44.java_pocs.jaxrs_cdi_jpa.entity.HomeworkAssignment;

public class HomeworkAssignmentDAO {
	
	@Inject
	private EntityManager entityManager;
	
	public List<HomeworkAssignment> findAll() {
		CriteriaQuery<HomeworkAssignment> query = entityManager.getCriteriaBuilder().createQuery(HomeworkAssignment.class);
		return entityManager.createQuery(query.select(query.from(HomeworkAssignment.class))).getResultList();
	}
	
	public HomeworkAssignment findById(Long id) {
		return entityManager.find(HomeworkAssignment.class, id);
	}
	
	public HomeworkAssignment save(HomeworkAssignment homeworkAssignment) {
		try {
			entityManager.getTransaction().begin();
			homeworkAssignment = entityManager.merge(homeworkAssignment);
			entityManager.getTransaction().commit();
			return homeworkAssignment;
		} catch (Throwable e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw e;
		}
	}
	
	public HomeworkAssignment removeById(Long id) {
		try {
			entityManager.getTransaction().begin();
			HomeworkAssignment homeworkAssignment = findById(id);
			entityManager.remove(homeworkAssignment);
			entityManager.getTransaction().commit();
			return homeworkAssignment;
		} catch (Throwable e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw e;
		}
	}
	
}
