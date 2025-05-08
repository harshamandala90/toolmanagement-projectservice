package com.taskmanagement.tool.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "project")
public class Project  {
	

	    
      
     	
	
	    @Id
	    @GeneratedValue
	    @Column(name = "project_id",nullable = false, unique = true)
	    private UUID projectid;
     	
	    
        @Column(name = "project_name",nullable = false, unique = true)
	    private String projectName;
        
        @Column(name = "status",nullable = false, unique = true)
	    private String status;
        
        @Column(name = "date_created",nullable = true, unique = true)
   	    private LocalDateTime  dateCreated;
        
        @Column(name = "date_modified",nullable = true, unique = true)
   	    private LocalDateTime  datemodified;
        
        
        @Column(name = "user_id",nullable = false, unique = false)
	    private String userId;
  
		
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public UUID getProjectid() {
			return projectid;
		}

		public void setProjectid(UUID projectid) {
			this.projectid = projectid;
		}
		
		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
		
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
		public LocalDateTime getDateCreated() {
			return dateCreated;
		}

		public void setDateCreated(LocalDateTime dateCreated) {
			this.dateCreated = dateCreated;
		}

		public LocalDateTime getDatemodified() {
			return datemodified;
		}

		public void setDatemodified(LocalDateTime datemodified) {
			this.datemodified = datemodified;
		}

	
}
