package com.carcity.CarCity.Backend.dtos;

import com.carcity.CarCity.Backend.dataentities.ApplicationUser;

public class ServiceProviderDTO {
	private long cell;
	private String name;

	
	public ServiceProviderDTO() {
		
		
	}
	
	public ServiceProviderDTO(ApplicationUser apu) {
		this.cell=apu.getCell();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCell() {
		return cell;
	}

	public void setCell(long cell) {
		this.cell = cell;
	}
	
	
}
