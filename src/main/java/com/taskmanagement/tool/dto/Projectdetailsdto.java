package com.taskmanagement.tool.dto;

import java.time.LocalDateTime;

public class Projectdetailsdto {
	private int username;
	private int projectid;
    private String projectname;
    private String projectstatus;
    
    private LocalDateTime projectdueDate;
   
    
	public int getUsername() {
		return username;
	}
	public void setUsername(int username) {
		this.username = username;
	}
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public LocalDateTime getProjectdueDate() {
		return projectdueDate;
	}
	public void setProjectdueDate(LocalDateTime projectdueDate) {
		this.projectdueDate = projectdueDate;
	}
	public String getProjectstatus() {
		return projectstatus;
	}
	public void setProjectstatus(String projectstatus) {
		this.projectstatus = projectstatus;
	}

    
   

   

    


	

}
