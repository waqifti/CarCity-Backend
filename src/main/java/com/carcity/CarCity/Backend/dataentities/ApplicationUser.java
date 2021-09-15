package com.carcity.CarCity.Backend.dataentities;

import java.util.Date;

import javax.persistence.Column;
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
import org.hibernate.annotations.UpdateTimestamp;



@Entity
public class ApplicationUser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	private long cell;
	
	private String password;
	
	private String sessiontoken;
	
	private String fcmtoken;
	
	@Enumerated (value = EnumType.STRING)
	private UserTypes ut;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	
	

	public String getFcmtoken() {
		return fcmtoken;
	}

	public void setFcmtoken(String fcmtoken) {
		this.fcmtoken = fcmtoken;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public UserTypes getUt() {
		return ut;
	}

	public void setUt(UserTypes ut) {
		this.ut = ut;
	}

	public long getCell() {
		return cell;
	}

	public void setCell(long cell) {
		this.cell = cell;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSessiontoken() {
		return sessiontoken;
	}

	public void setSessiontoken(String sessiontoken) {
		this.sessiontoken = sessiontoken;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
}
