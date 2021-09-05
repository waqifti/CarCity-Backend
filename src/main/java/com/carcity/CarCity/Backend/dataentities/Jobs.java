package com.carcity.CarCity.Backend.dataentities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Jobs {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date dbentryat;
	
	
	@Enumerated (value = EnumType.STRING)
	private JobState state;
	
	
	private String description;
	private String notes;
	
	
	@OneToOne
	private ApplicationUser assignedto;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getDbentryat() {
		return dbentryat;
	}


	public void setDbentryat(Date dbentryat) {
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


	public ApplicationUser getAssignedto() {
		return assignedto;
	}


	public void setAssignedto(ApplicationUser assignedto) {
		this.assignedto = assignedto;
	}
	
	
	
	
	
	
	
	
	
}
