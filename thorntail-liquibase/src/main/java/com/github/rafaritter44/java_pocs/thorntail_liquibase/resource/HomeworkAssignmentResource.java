package com.github.rafaritter44.java_pocs.thorntail_liquibase.resource;

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

import com.github.rafaritter44.java_pocs.thorntail_liquibase.dao.HomeworkAssignmentDAO;
import com.github.rafaritter44.java_pocs.thorntail_liquibase.entity.HomeworkAssignment;

@RequestScoped
@Transactional
@Path("/homework-assignment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HomeworkAssignmentResource {
	
	@Inject
	private HomeworkAssignmentDAO homeworkAssignmentDAO;
	
	@GET
	public Response findAll() {
		return Response.ok(homeworkAssignmentDAO.findAll()).build();
	}
	
	@GET
	@Path("/{id}")
	public Response findById(@PathParam("id") Long id) {
		return Response.ok(homeworkAssignmentDAO.findById(id)).build();
	}
	
	@POST
	public Response save(HomeworkAssignment homeworkAssignment) {
		return Response.ok(homeworkAssignmentDAO.save(homeworkAssignment)).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") Long id) {
		return Response.ok(homeworkAssignmentDAO.removeById(id)).build();
	}
	
}
