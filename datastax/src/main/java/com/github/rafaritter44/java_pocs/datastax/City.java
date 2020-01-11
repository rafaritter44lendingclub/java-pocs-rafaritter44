package com.github.rafaritter44.java_pocs.datastax;

import java.math.BigDecimal;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "cluster_test", name = "city")
public class City {
	
	@PartitionKey
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private BigDecimal distance;
	
	@ClusteringColumn
	private Long to_id;
	
	public City() {}
	
	public City(Long id, String name, BigDecimal distance, Long to_id) {
		this.id = id;
		this.name = name;
		this.distance = distance;
		this.to_id = to_id;
	}

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

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public Long getTo_id() {
		return to_id;
	}

	public void setTo_id(Long to_id) {
		this.to_id = to_id;
	}
	
}
