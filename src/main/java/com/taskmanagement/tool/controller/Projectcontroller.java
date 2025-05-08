package com.taskmanagement.tool.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.taskmanagement.tool.dto.Projectdetailsdto;
import com.taskmanagement.tool.model.Project;
import com.taskmanagement.tool.service.Projectservice;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")

public class Projectcontroller {

	@Autowired
	private Projectservice projectservice;
	 @Autowired
	    private ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger(Projectcontroller.class);
	
	@PostMapping("/createproject")
	@ResponseBody
	public ResponseEntity<ObjectNode> createproject(@RequestBody Projectdetailsdto projectdetailsdto, HttpServletRequest request) {
	    logger.info("Just entered createproject() method");

	    try {
	        Object userIdObj = request.getSession().getAttribute("userId");
	        if (userIdObj == null) {
	            logger.warn("User ID not found in session.");
	            ObjectNode errorResponse = objectMapper.createObjectNode();
	            errorResponse.put("error", "User ID is not available in session.");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	        }

	        Project project = new Project();
	        project.setUserId(userIdObj.toString());
	        project.setProjectName(projectdetailsdto.getProjectname());
	        project.setStatus(projectdetailsdto.getProjectstatus());
	        project.setDateCreated(LocalDateTime.now());

	        projectservice.saveProject(project);

	        ObjectNode projectNode = objectMapper.createObjectNode();
	        projectNode.put("projectId", project.getProjectid().toString());
	        projectNode.put("projectName", project.getProjectName());
	        projectNode.put("status", project.getStatus());
	        projectNode.put("dateCreated", project.getDateCreated().toString());

	        ObjectNode responseNode = objectMapper.createObjectNode();
	        responseNode.put("message", "Project successfully created.");
	        responseNode.set("project", projectNode);

	        logger.info("Just leaving createproject() method from Projectcontroller");
	        return ResponseEntity.status(HttpStatus.CREATED).body(responseNode);

	    } catch (Exception e) {
	        logger.debug("Exception in createproject() method: " + e.getMessage(), e);

	        ObjectNode errorResponse = new ObjectMapper().createObjectNode();
	        errorResponse.put("error", "Project creation failed");
	        errorResponse.put("details", e.getMessage());

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

	
	@GetMapping("/projects/{projectid}")
    @ResponseBody
    public ResponseEntity<?> getprojectById(@PathVariable UUID projectid) {
		try {
			
		logger.info("Just entered getprojectById() method for projectid:{}",projectid);
        Optional<Project> projectOptional = projectservice.getprojectbyid(projectid);
        if (projectOptional.isPresent()) {
            return ResponseEntity.ok(projectOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("project not found with ID: " + projectid);
        }
        
		}catch (Exception e) {
	    	logger.debug("Exception in getprojectById() method" + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("project fetching failed: " + e.getMessage());
	    }
    }
	
	@DeleteMapping("/projects/{projectid}")
	public ResponseEntity<String> deleteProjectTask(@PathVariable UUID projectid) {
	    boolean isDeleted = projectservice.deleteProjectById(projectid);

	    if (isDeleted) {
	        return ResponseEntity.ok("Project successfully deleted");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("Project not found with ID: " + projectid);
	    }
	}
	
	@PutMapping("/projects/{projectid}")
	@ResponseBody
	public ResponseEntity<ObjectNode> updateProjectById(@RequestBody Projectdetailsdto projectdetailsdto, @PathVariable UUID projectid) {
	    logger.info("Just entered updateProjectById() method for project: {}", projectid);

	    try {
	        if (!projectservice.existsById(projectid)) {
	            ObjectNode errorNode = objectMapper.createObjectNode();
	            errorNode.put("error", "Project not found with ID: " + projectid);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorNode);
	        }

	        Optional<Project> projectOptional = projectservice.getprojectbyid(projectid);
	        if (projectOptional.isEmpty()) {
	            ObjectNode errorNode = objectMapper.createObjectNode();
	            errorNode.put("error", "Project not found in optional result with ID: " + projectid);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorNode);
	        }

	        Project project = projectOptional.get();

	        if (projectdetailsdto.getProjectname() != null) {
	            project.setProjectName(projectdetailsdto.getProjectname());
	        }

	        if (projectdetailsdto.getProjectstatus() != null) {
	            project.setStatus(projectdetailsdto.getProjectstatus());
	        }

	        project.setDatemodified(LocalDateTime.now());

	        projectservice.updateProject(project);

	        ObjectNode successNode = objectMapper.createObjectNode();
	        successNode.put("message", "Project successfully updated");
	        successNode.put("projectId", projectid.toString());

	        logger.info("Just leaving updateProjectById() method for project: {}", projectid);
	        return ResponseEntity.ok(successNode);

	    } catch (Exception e) {
	        logger.debug("Exception in updateProjectById() method: " + e.getMessage(), e);

	        ObjectNode errorNode = new ObjectMapper().createObjectNode();
	        errorNode.put("error", "Error updating project");
	        errorNode.put("details", e.getMessage());

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorNode);
	    }
	}

	
	
	@GetMapping("/projects/user/{userId}")
    @ResponseBody
     public List<Project> getprojectsbyusername(@PathVariable String userId)
    {
		List<Project> projectList = projectservice.getprojectbyuserId(userId);
		System.out.println("project list size " + projectList.size());
        return projectList;
        
    }

	
	
	}



