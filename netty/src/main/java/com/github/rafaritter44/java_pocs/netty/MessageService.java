package com.github.rafaritter44.java_pocs.netty;

import java.util.HashMap;
import java.util.UUID;

public class MessageService {
	
	private static final HashMap<UUID, MessageResponse> MESSAGES = new HashMap<>();
	
	public static MessageResponse save(final MessageRequest request) {
		final MessageResponse response = new MessageResponse(request.getContent(), request.getSender());
		MESSAGES.put(response.getId(), response);
		return response;
	}
	
	public static HashMap<UUID, MessageResponse> findAll() {
		return MESSAGES;
	}
	
}
