package com.github.rafaritter44.java_pocs.thorntail_hazelcast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HomeworkAssignment implements java.io.Serializable {
	
	private static final long serialVersionUID = 4209041566834714921L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	private Integer estimatedDeadlineInDays;
	
	public HomeworkAssignment() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getEstimatedDeadlineInDays() {
		return estimatedDeadlineInDays;
	}

	public void setEstimatedDeadlineInDays(Integer estimatedDeadlineInDays) {
		this.estimatedDeadlineInDays = estimatedDeadlineInDays;
	}
	
}
