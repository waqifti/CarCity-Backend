package com.carcity.CarCity.Backend.dtos;

import com.carcity.CarCity.Backend.dataentities.ApplicationUser;

public class ServiceProviderDTO {
	private long cell;
	
	public ServiceProviderDTO() {
		
		
	}
	
	public ServiceProviderDTO(ApplicationUser apu) {
		this.cell=apu.getCell();
	}

	public long getCell() {
		return cell;
	}

	public void setCell(long cell) {
		this.cell = cell;
	}
	
	
}
