package com.carcity.CarCity.Backend.dtos;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.carcity.CarCity.Backend.dataentities.LocationRecord;

public class LocationDTO {
	private long longi;
	private long lati;
	private String recordedat;
	private String dbentryat;

	public LocationDTO() {

	}

	public LocationDTO(LocationRecord lr) {
		this.longi=lr.getLongi();
		this.lati=lr.getLati();
		this.setRecordedat(lr.getTime());
		this.setDbentryat(lr.getCreatedAt().toString());
	}
	public long getLongi() {
		return longi;
	}
	public void setLongi(long longi) {
		this.longi = longi;
	}
	public long getLati() {
		return lati;
	}
	public void setLati(long lati) {
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
