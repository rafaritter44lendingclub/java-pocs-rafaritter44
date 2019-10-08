package com.github.rafaritter44.java_pocs.thorntail_ignite.cache;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.configuration.ClientConfiguration;

import com.github.rafaritter44.java_pocs.thorntail_ignite.entity.HomeworkAssignment;

@ApplicationScoped
public class HomeworkAssignmentCache {
	
	private final ClientCache<Long, HomeworkAssignment> cache;
	
	public HomeworkAssignmentCache() {
		ClientConfiguration config = new ClientConfiguration().setAddresses("ignite:10800");
		cache = Ignition.startClient(config).getOrCreateCache("homework-assignment");
	}
	
	public Optional<HomeworkAssignment> findById(Long id) {
		return Optional.ofNullable(cache.get(id));
	}
	
	public HomeworkAssignment save(HomeworkAssignment homeworkAssignment) {
		cache.put(homeworkAssignment.getId(), homeworkAssignment);
		return homeworkAssignment;
	}
	
	public Optional<HomeworkAssignment> removeById(Long id) {
		return Optional.ofNullable(cache.getAndRemove(id));
	}
	
}
