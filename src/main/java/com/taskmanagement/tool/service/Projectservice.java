package com.taskmanagement.tool.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanagement.tool.model.Project;

import com.taskmanagement.tool.repository.Jpaprojectrepository;


@Service
public class Projectservice {

	@Autowired
    private Jpaprojectrepository projectRepository;
	
	public void saveProject(Project project) {
		projectRepository.save(project);		
	}

	public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

	public Optional<Project> getprojectbyid(UUID projectid) {
		return projectRepository.findById(projectid);
		
	}

	public void updateProject(Project project) {
		  projectRepository.save(project);
		}

	public Optional<Project> getprojectsbyuserid(UUID userid) {
		return projectRepository.findById(userid);
	}

	public boolean deleteProjectById(UUID projectid) {
	    if (projectRepository.existsById(projectid)) {
	        projectRepository.deleteById(projectid);
	        return true;
	    } else {
	        return false;
	    }
	}

	public boolean existsById(UUID projectid) {
	    return projectRepository.existsById(projectid);
	}

	public List<Project> getprojectbyuserId(String userId) {
		return projectRepository.findByuserId(userId);
	}
}
