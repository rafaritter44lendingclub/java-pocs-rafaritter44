package com.github.rafaritter44.java_pocs.spring.graphql.entity;

import java.util.UUID;

public class Book {
	
	private UUID id;
	private UUID authorId;
	private String name;
	private int pageCount;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public UUID getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(UUID authorId) {
		this.authorId = authorId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
}
