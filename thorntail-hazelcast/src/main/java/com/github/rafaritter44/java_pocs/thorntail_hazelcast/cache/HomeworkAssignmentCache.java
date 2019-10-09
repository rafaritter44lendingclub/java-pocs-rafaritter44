package com.github.rafaritter44.java_pocs.thorntail_hazelcast.cache;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import com.github.rafaritter44.java_pocs.thorntail_hazelcast.entity.HomeworkAssignment;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.IMap;

@ApplicationScoped
public class HomeworkAssignmentCache {
	
	private final IMap<Long, HomeworkAssignment> cache;
	
	public HomeworkAssignmentCache() {
		@SuppressWarnings("deprecation")
		ClientConfig config = new ClientConfig().addAddress("hazelcast");
		cache = HazelcastClient.newHazelcastClient(config).getMap("homework-assignment");
	}
	
	public Optional<HomeworkAssignment> findById(Long id) {
		return Optional.ofNullable(cache.get(id));
	}
	
	public HomeworkAssignment save(HomeworkAssignment homeworkAssignment) {
		cache.put(homeworkAssignment.getId(), homeworkAssignment);
		return homeworkAssignment;
	}
	
	public Optional<HomeworkAssignment> removeById(Long id) {
		return Optional.ofNullable(cache.remove(id));
	}
	
}
