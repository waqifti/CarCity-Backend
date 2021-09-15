package com.carcity.CarCity.Backend.RestContollers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carcity.CarCity.Backend.dataentities.AppState;
import com.carcity.CarCity.Backend.dataentities.ApplicationUser;
import com.carcity.CarCity.Backend.dataentities.ApplicationUserRepo;
import com.carcity.CarCity.Backend.dataentities.LocationProvider;
import com.carcity.CarCity.Backend.dataentities.LocationRecord;
import com.carcity.CarCity.Backend.dataentities.LocationRecordRepo;
import com.carcity.CarCity.Backend.dataentities.UserTypes;

@RestController
public class ServiceProviderController {
	
	@Autowired ApplicationUserRepo objApplicationUserRepo;
	@Autowired LocationRecordRepo objLocationRecordRepo;
	
	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/ServiceProvider/UpdateLocation"} )
	public ResponseEntity<?> UpdateServiceProviderLocation(@RequestHeader String sessiontoken,
			@RequestParam double lati,
			@RequestParam double longi,
			@RequestParam String time,
			@RequestParam AppState appstate,
			@RequestParam LocationProvider locationprovider) throws Exception {

		System.out.println("====UpdateLocation=====");
		System.out.println("====lati====="+lati);
		System.out.println("====longi====="+longi);
		System.out.println("====time====="+time);

		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body("Wrong sessiontoken");
		} else {

			if(apu.getUt()==UserTypes.ServiceProvider) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body("Cannot call this api for "+apu.getUt().toString());
			}


		}
		
		//Sep 7, 2021 6:01:18 PM
	
	    Date date1=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a").parse(time);  
	    
		

		LocationRecord toSave=new LocationRecord();
		toSave.setLati(lati);
		toSave.setLongi(longi);
		toSave.setTimeondevice(date1);
		toSave.setOf(apu);
		toSave.setAppstate(appstate);
		toSave.setLocationprovider(locationprovider);

		objLocationRecordRepo.saveAndFlush(toSave);


		return ResponseEntity
				.status(HttpStatus.OK)
				.body("");






	}
}
