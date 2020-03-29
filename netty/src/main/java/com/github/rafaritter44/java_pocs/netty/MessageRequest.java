package com.github.rafaritter44.java_pocs.netty;

public class MessageRequest {
	
	private final String content;
	private final String sender;
	
	public MessageRequest(final String content, final String sender) {
		this.content = content;
		this.sender = sender;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getSender() {
		return sender;
	}
	
}
