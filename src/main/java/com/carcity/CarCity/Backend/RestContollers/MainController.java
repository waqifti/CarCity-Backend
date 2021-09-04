package com.carcity.CarCity.Backend.RestContollers;

import java.util.UUID;

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
public class MainController {

	@Autowired ApplicationUserRepo objApplicationUserRepo;
	@Autowired LocationRecordRepo objLocationRecordRepo;


	@RequestMapping(method=RequestMethod.GET,value={"/test"} )
	public ResponseEntity<?> test() throws Exception {

		ApplicationUser loggedInUser = new ApplicationUser();



		return ResponseEntity
				.status(HttpStatus.OK)
				.body("OK");

	}



	

	@RequestMapping(method=RequestMethod.POST,value={"/Login"} )
	public ResponseEntity<?> Login(@RequestParam long cell,
			@RequestParam String password,
			@RequestParam UserTypes ut) throws Exception {



		ApplicationUser apu = objApplicationUserRepo.findByCell(cell);

		if(apu==null) {
			apu = new ApplicationUser();
			apu.setCell(cell);
			apu.setPassword(password);
			apu.setUt(ut);
		} else {
			if(apu.getPassword().equals(password)) {
				if(apu.getUt()==ut) {

				} else {
					return ResponseEntity
							.status(HttpStatus.METHOD_FAILURE)
							.body("Please specify correct ut.");
				}

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body("Wrong password");
			}
		}

		apu.setSessiontoken(UUID.randomUUID().toString());


		objApplicationUserRepo.saveAndFlush(apu);


		return ResponseEntity
				.status(HttpStatus.OK)
				.body(apu.getSessiontoken());






	}
}
