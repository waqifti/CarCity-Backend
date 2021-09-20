package com.carcity.CarCity.Backend.dtos;

public class ServiceProviderUserDTO {
	private long cell;
	
	private double currentlongi;
	private double currentlati;
	public long getCell() {
		return cell;
	}
	public void setCell(long cell) {
		this.cell = cell;
	}
	public double getCurrentlongi() {
		return currentlongi;
	}
	public void setCurrentlongi(double currentlongi) {
		this.currentlongi = currentlongi;
	}
	public double getCurrentlati() {
		return currentlati;
	}
	public void setCurrentlati(double currentlati) {
		this.currentlati = currentlati;
	}
	
	
}
