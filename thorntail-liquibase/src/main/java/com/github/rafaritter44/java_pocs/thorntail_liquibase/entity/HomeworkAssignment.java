package com.github.rafaritter44.java_pocs.thorntail_liquibase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "homework_assignment")
public class HomeworkAssignment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	@Column(name = "estimated_deadline_in_days")
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
