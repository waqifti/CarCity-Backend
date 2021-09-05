package com.carcity.CarCity.Backend.dataentities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class LocationRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	private ApplicationUser of;
	
	
	private double longi;
	private double lati;
	
	
	
	private Date timeondevice;
	
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public ApplicationUser getOf() {
		return of;
	}


	public void setOf(ApplicationUser of) {
		this.of = of;
	}


	public double getLongi() {
		return longi;
	}


	public void setLongi(double longi) {
		this.longi = longi;
	}


	public double getLati() {
		return lati;
	}


	public void setLati(double lati) {
		this.lati = lati;
	}


	public Date getTimeondevice() {
		return timeondevice;
	}


	public void setTimeondevice(Date timeondevice) {
		this.timeondevice = timeondevice;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	
	
}
