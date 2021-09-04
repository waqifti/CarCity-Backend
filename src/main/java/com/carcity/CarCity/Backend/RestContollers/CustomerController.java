package com.carcity.CarCity.Backend.RestContollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carcity.CarCity.Backend.dataentities.ApplicationUser;
import com.carcity.CarCity.Backend.dataentities.ApplicationUserRepo;
import com.carcity.CarCity.Backend.dataentities.LocationRecord;
import com.carcity.CarCity.Backend.dataentities.LocationRecordRepo;
import com.carcity.CarCity.Backend.dataentities.UserTypes;

@RestController
public class CustomerController {
	@Autowired ApplicationUserRepo objApplicationUserRepo;
	@Autowired LocationRecordRepo objLocationRecordRepo;
	
	
	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/Customer/UpdateLocation"} )
	public ResponseEntity<?> UpdateCustomerLocation(@RequestHeader String sessiontoken,
			@RequestParam long lati,
			@RequestParam long longi,
			@RequestParam String time) throws Exception {



		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body("Wrong sessiontoken");
		} else {

			if(apu.getUt()==UserTypes.Customer) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body("Cannot call this api for "+apu.getUt().toString());
			}


		}

		LocationRecord toSave=new LocationRecord();
		toSave.setLati(lati);
		toSave.setLongi(longi);
		toSave.setTime(time);
		toSave.setOf(apu);


		objLocationRecordRepo.saveAndFlush(toSave);


		return ResponseEntity
				.status(HttpStatus.OK)
				.body("");






	}

}
