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
public class LocationRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	private ApplicationUser of;
	
	
	private double longi;
	private double lati;
	
	
	
	private Date timeondevice;
	private String timeondeviceasstring;
	
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Enumerated (value = EnumType.STRING)
	private AppState appstate;
	
	@Enumerated (value = EnumType.STRING)
	private LocationProvider locationprovider;
	
	
	


	public String getTimeondeviceasstring() {
		return timeondeviceasstring;
	}


	public void setTimeondeviceasstring(String timeondeviceasstring) {
		this.timeondeviceasstring = timeondeviceasstring;
	}


	public LocationProvider getLocationprovider() {
		return locationprovider;
	}


	public void setLocationprovider(LocationProvider locationprovider) {
		this.locationprovider = locationprovider;
	}


	public AppState getAppstate() {
		return appstate;
	}


	public void setAppstate(AppState appstate) {
		this.appstate = appstate;
	}


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


	@Override
	public String toString() {
		return "LocationRecord [id=" + id + ", of=" + of + ", longi=" + longi + ", lati=" + lati + ", timeondevice="
				+ timeondevice + ", timeondeviceasstring=" + timeondeviceasstring + ", createdAt=" + createdAt
				+ ", appstate=" + appstate + ", locationprovider=" + locationprovider + "]";
	}

	
	
}
