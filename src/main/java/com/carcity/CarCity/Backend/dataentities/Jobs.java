package com.carcity.CarCity.Backend.dataentities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table( uniqueConstraints={
		@UniqueConstraint(columnNames={"assignedto_id"})
})
public class Jobs {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;


	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date dbentryat;


	@ElementCollection
	Set<String> jobtypes=new HashSet<String>();


	@Enumerated (value = EnumType.STRING)
	private JobState state;


	private String description;
	private String notes;


	private Double longi;
	private Double lati;


	@OneToOne
	private ApplicationUser managedby;

	@OneToOne
	private ApplicationUser createdby;

	@OneToOne(fetch = FetchType.EAGER)
	private ApplicationUser assignedto;

	@OneToOne
	private ApplicationUser completedby;


	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduledAt;




	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;





	

	public Double getLongi() {
		return longi;
	}


	public void setLongi(Double longi) {
		this.longi = longi;
	}


	public Double getLati() {
		return lati;
	}


	public void setLati(Double lati) {
		this.lati = lati;
	}


	public ApplicationUser getCompletedby() {
		return completedby;
	}


	public void setCompletedby(ApplicationUser completedby) {
		this.completedby = completedby;
	}


	public ApplicationUser getAssignedto() {
		return assignedto;
	}


	public void setAssignedto(ApplicationUser assignedto) {
		this.assignedto = assignedto;
	}





	public ApplicationUser getCreatedby() {
		return createdby;
	}


	public void setCreatedby(ApplicationUser createdby) {
		this.createdby = createdby;
	}


	public Date getScheduledAt() {
		return scheduledAt;
	}


	public void setScheduledAt(Date scheduledAt) {
		this.scheduledAt = scheduledAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


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



	public ApplicationUser getManagedby() {
		return managedby;
	}


	public void setManagedby(ApplicationUser managedby) {
		this.managedby = managedby;
	}


	public Set<String> getJobtypes() {
		return jobtypes;
	}


	public void setJobtypes(Set<String> jobtypes) {
		this.jobtypes = jobtypes;
	}









}
