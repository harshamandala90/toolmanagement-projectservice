package com.taskmanagement.tool.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanagement.tool.model.Project;


@Repository
public interface Jpaprojectrepository extends JpaRepository<Project, UUID> {

	List<Project> findByuserId(String userId);

}