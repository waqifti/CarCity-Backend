package com.carcity.CarCity.Backend.dtos;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.carcity.CarCity.Backend.dataentities.ApplicationUser;
import com.carcity.CarCity.Backend.dataentities.JobState;
import com.carcity.CarCity.Backend.dataentities.Jobs;

public class JobDTO {
	
	private int id;
	
	
	
	private String dbentryat;
	
	

	private JobState state;
	
	
	private String description;
	private String notes;
	
	
	
	private String assignedto;

	public JobDTO() {
		
		
	}
	
	public JobDTO(Jobs j) {
		this.id=j.getId();
		this.notes=j.getNotes();
		this.description=j.getDescription();
		this.state=j.getState();
		this.dbentryat=j.getDbentryat().toString();
		this.assignedto= ""+j.getAssignedto().getCell()+" ("+j.getAssignedto().getUt().toString()+")";
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getDbentryat() {
		return dbentryat;
	}



	public void setDbentryat(String dbentryat) {
		this.dbentryat = dbentryat;
	}



	public JobState getState() {
		return state;
	}



	public void setState(JobState state) {
		this.state = state;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getNotes() {
		return notes;
	}



	public void setNotes(String notes) {
		this.notes = notes;
	}



	public String getAssignedto() {
		return assignedto;
	}



	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}
	
	
	
	
	
}
