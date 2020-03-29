package com.github.rafaritter44.java_pocs.netty;

import java.util.UUID;

public class MessageResponse {
	
	private final UUID id;
	private final String content;
	private final String sender;
	private final boolean received;
	
	public MessageResponse(final String content, final String sender) {
		this(UUID.randomUUID(), content, sender, true);
	}
	
	public MessageResponse(final UUID id, final String content, final String sender, final boolean received) {
		this.id = id;
		this.content = content;
		this.sender = sender;
		this.received = received;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getSender() {
		return sender;
	}
	
	public boolean wasReceived() {
		return received;
	}
	
}
