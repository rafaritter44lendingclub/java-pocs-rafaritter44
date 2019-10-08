package com.github.rafaritter44.java_pocs.enterprise.resource;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.rafaritter44.java_pocs.enterprise.dao.HomeworkAssignmentDAO;
import com.github.rafaritter44.java_pocs.enterprise.entity.HomeworkAssignment;

@Stateless
@Path("/homework-assignment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HomeworkAssignmentResource {
	
	@EJB
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
	public Response removeById(@PathParam("id") Long id) {
		return Response.ok(homeworkAssignmentDAO.removeById(id)).build();
	}
	
}
