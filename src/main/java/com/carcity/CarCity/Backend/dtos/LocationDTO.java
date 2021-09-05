package com.carcity.CarCity.Backend.dtos;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.carcity.CarCity.Backend.dataentities.LocationRecord;

public class LocationDTO {
	private double longi;
	private double lati;
	private String recordedat;
	private String dbentryat;

	public LocationDTO() {

	}

	public LocationDTO(LocationRecord lr) {
		this.longi=lr.getLongi();
		this.lati=lr.getLati();
		this.setRecordedat(lr.getTimeondevice().toString());
		this.setDbentryat(lr.getCreatedAt().toString());
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
	public String getRecordedat() {
		return recordedat;
	}
	public void setRecordedat(String recordedat) {
		this.recordedat = recordedat;
	}
	public String getDbentryat() {
		return dbentryat;
	}
	public void setDbentryat(String dbentryat) {
		this.dbentryat = dbentryat;
	}




}
