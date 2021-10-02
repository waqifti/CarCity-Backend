package com.carcity.CarCity.Backend.RestContollers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carcity.CarCity.Backend.PushNotificationUtil;
import com.carcity.CarCity.Backend.dataentities.ApplicationUser;
import com.carcity.CarCity.Backend.dataentities.ApplicationUserRepo;
import com.carcity.CarCity.Backend.dataentities.LocationRecord;
import com.carcity.CarCity.Backend.dataentities.LocationRecordRepo;
import com.carcity.CarCity.Backend.dataentities.UserTypes;

@RestController
public class MainController {

	@Autowired ApplicationUserRepo objApplicationUserRepo;
	@Autowired LocationRecordRepo objLocationRecordRepo;
	@Autowired PushNotificationUtil objPushNotificationUtil;
	Logger logger = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(method=RequestMethod.GET,value={"/test"} )
	public ResponseEntity<?> test() throws Exception {

		ApplicationUser loggedInUser = new ApplicationUser();



		return ResponseEntity
				.status(HttpStatus.OK)
				.body("OK");

	}


	@RequestMapping(method=RequestMethod.POST,value={"/GetJobTypes"} )
	public ResponseEntity<?> GetJobTypes(@RequestHeader String sessiontoken) throws Exception {



		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body("Wrong sessiontoken");
		} else {
			List<String> jobtypes=new ArrayList<String>();
			jobtypes.add("Wash");
			jobtypes.add("Wax");
			jobtypes.add("Interior Cleaning");
			jobtypes.add("Package 1");
			jobtypes.add("Package 2");

			return ResponseEntity
					.status(HttpStatus.OK)
					.body(jobtypes);


		}










	}


	@RequestMapping(method=RequestMethod.POST,value={"/Login"} )
	public ResponseEntity<?> Login(@RequestParam long cell,
			@RequestParam String password,
			@RequestParam(required=false) String fcmtoken,
			@RequestParam UserTypes ut) throws Exception {

		System.out.println("====Login=====");
		System.out.println("====cell====="+cell);
		System.out.println("====password====="+password);
		System.out.println("====fcmtoken====="+fcmtoken);

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
		
		
		apu.setFcmtoken(fcmtoken);
		apu.setSessiontoken(UUID.randomUUID().toString());


		objApplicationUserRepo.saveAndFlush(apu);
		if(fcmtoken!=null && !fcmtoken.trim().isEmpty()) {
			try {
				objPushNotificationUtil.sendNotificationToAndroid(fcmtoken, "Car City", "Login kurney key liye shukria.");
			} catch (Exception ex) {
				logger.error("",ex);
			}
		
		}

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(apu.getSessiontoken());






	}
}
