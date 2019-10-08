package com.github.rafaritter44.java_pocs.thorntail_ignite.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.rafaritter44.java_pocs.thorntail_ignite.entity.HomeworkAssignment;
import com.github.rafaritter44.java_pocs.thorntail_ignite.service.HomeworkAssignmentService;

@RequestScoped
@Transactional
@Path("/homework-assignment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HomeworkAssignmentResource {
	
	@Inject
	private HomeworkAssignmentService service;
	
	@GET
	public Response findAll() {
		return Response.ok(service.findAll()).build();
	}
	
	@GET
	@Path("/{id}")
	public Response findById(@PathParam("id") Long id) {
		return Response.ok(service.findById(id)).build();
	}
	
	@POST
	public Response save(HomeworkAssignment homeworkAssignment) {
		return Response.ok(service.save(homeworkAssignment)).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeById(@PathParam("id") Long id) {
		return Response.ok(service.removeById(id)).build();
	}	
	
}
