package com.github.rafaritter44.java_pocs.spring_core.service;

public class GoodbyeService {
	
	private String goodbyeMessage;
	
	public String goodbye() {
		return goodbyeMessage;
	}
	
	public void setGoodbyeMessage(String goodbyeMessage) {
		this.goodbyeMessage = goodbyeMessage;
	}

}
