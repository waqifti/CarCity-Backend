package com.carcity.CarCity.Backend.RestContollers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
import com.carcity.CarCity.Backend.dataentities.JobState;
import com.carcity.CarCity.Backend.dataentities.Jobs;
import com.carcity.CarCity.Backend.dataentities.JobsRepo;
import com.carcity.CarCity.Backend.dataentities.LocationRecord;
import com.carcity.CarCity.Backend.dataentities.LocationRecordRepo;
import com.carcity.CarCity.Backend.dataentities.UserTypes;
import com.carcity.CarCity.Backend.dtos.JobDTO;
import com.carcity.CarCity.Backend.dtos.LocationDTO;
import com.carcity.CarCity.Backend.dtos.ServiceProviderDTO;

@RestController
public class AdminPortalContoller {

	@Autowired ApplicationUserRepo objApplicationUserRepo;
	@Autowired LocationRecordRepo objLocationRecordRepo;
	@Autowired JobsRepo objJobsRepo;
	@Autowired PushNotificationUtil objPushNotificationUtil;

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/AdminPortal/changeJobInfo"} )
	public ResponseEntity<?> changeJobInfo(@RequestHeader String sessiontoken,
			@RequestParam int jobid,
			@RequestParam(required=false) Integer assignedto,
			@RequestParam(required=false) JobState newstate) throws Exception {

		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body("Wrong sessiontoken");
		} else {

			if(apu.getUt()==UserTypes.AdminPortal) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body("Cannot call this api for "+apu.getUt().toString());
			}


		}
		
		Jobs job = objJobsRepo.getById(jobid);
		
		if(assignedto!=null) {
			ApplicationUser sp=objApplicationUserRepo.getOne(assignedto);
			if(sp==null || sp.getUt()!=UserTypes.ServiceProvider) {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body("Assigned to must be sp");
			}
			job.setAssignedto(sp);
			
			
			if(sp.getFcmtoken()!=null) {
				objPushNotificationUtil.sendNotificationToAndroid(sp.getFcmtoken(), "Car City", "Ap ko ek new job dey di gaye hay. Mazeed janey key liye Car City ki application kholien.");
			}
			
			if(apu.getFcmtoken()!=null) {
				objPushNotificationUtil.sendNotificationToAndroid(apu.getFcmtoken(), "Car City", "Ap ko ek service provider assign kurdiya gya hay. Mazeed janey key liye Car City ki application kholien.");
			}
			
			
			
		}
		
		if(newstate!=null) {
			job.setState(newstate);
		}
		


		
		
		
		objJobsRepo.saveAndFlush(job);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body("");

	}


	@RequestMapping(method=RequestMethod.GET,value={"/Authenticated/AdminPortal/getAllJobs"} )
	public ResponseEntity<?> getAllJobs(@RequestHeader String sessiontoken) throws Exception {
		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body("Wrong sessiontoken");
		} else {

			if(apu.getUt()==UserTypes.AdminPortal) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body("Cannot call this api for "+apu.getUt().toString());
			}


		}


		List<Jobs> jobs = objJobsRepo.findAll();


		List<JobDTO> toReturn = new ArrayList<JobDTO>();
		for(Jobs i:jobs) {
			toReturn.add(new JobDTO(i));
		}



		return ResponseEntity
				.status(HttpStatus.OK)
				.body(toReturn);

	}

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/AdminPortal/SendAMessage"} )
	public ResponseEntity<?> SendAMessage(@RequestHeader String sessiontoken,
			@RequestParam List<Long> cellnos,@RequestParam String message) throws Exception {
		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body("Wrong sessiontoken");
		} else {

			if(apu.getUt()==UserTypes.AdminPortal) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body("Cannot call this api for "+apu.getUt().toString());
			}


		}

		if(cellnos==null || cellnos.size()==0) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body("Recipients Not Set.");
		}

		for(Long cellNo:cellnos) {
			ApplicationUser sendTo = objApplicationUserRepo.findByCell(cellNo);
			if(sendTo!=null) {
				if(sendTo.getFcmtoken()!=null) {
					objPushNotificationUtil.sendNotificationToAndroid(sendTo.getFcmtoken(),
							"Car City", message);
				}
			}
		}



		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Sent.");

	}

	@RequestMapping(method=RequestMethod.GET,value={"/Authenticated/AdminPortal/GetAllCustomers"} )
	public ResponseEntity<?> GetAllCustomers(@RequestHeader String sessiontoken) throws Exception {

		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body("Wrong sessiontoken");
		} else {

			if(apu.getUt()==UserTypes.AdminPortal) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body("Cannot call this api for "+apu.getUt().toString());
			}


		}


		List<ApplicationUser> serviceproviders = objApplicationUserRepo.findAllByUt(UserTypes.Customer);


		List<ServiceProviderDTO> toReturn = new ArrayList<ServiceProviderDTO>();
		for(ApplicationUser i:serviceproviders) {
			toReturn.add(new ServiceProviderDTO(i));
		}



		return ResponseEntity
				.status(HttpStatus.OK)
				.body(toReturn);






	}

	@RequestMapping(method=RequestMethod.GET,value={"/Authenticated/AdminPortal/GetAllServiceProviders"} )
	public ResponseEntity<?> GetAllServiceProviders(@RequestHeader String sessiontoken) throws Exception {

		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body("Wrong sessiontoken");
		} else {

			if(apu.getUt()==UserTypes.AdminPortal) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body("Cannot call this api for "+apu.getUt().toString());
			}


		}


		List<ApplicationUser> serviceproviders = objApplicationUserRepo.findAllByUt(UserTypes.ServiceProvider);


		List<ServiceProviderDTO> toReturn = new ArrayList<ServiceProviderDTO>();
		for(ApplicationUser i:serviceproviders) {
			toReturn.add(new ServiceProviderDTO(i));
		}



		return ResponseEntity
				.status(HttpStatus.OK)
				.body(toReturn);






	}


	@RequestMapping(method=RequestMethod.GET,value={"/Authenticated/AdminPortal/GetUsersRecordedLocations"} )
	public ResponseEntity<?> GetUsersRecordedLocations(@RequestHeader String sessiontoken,
			long cell) throws Exception {

		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body("Wrong sessiontoken");
		} else {

			if(apu.getUt()==UserTypes.AdminPortal) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body("Cannot call this api for "+apu.getUt().toString());
			}


		}

		ApplicationUser sp=objApplicationUserRepo.findByCell(cell);


		List<LocationRecord> locations = objLocationRecordRepo.findAllByOf(sp);


		List<LocationDTO> toReturn = new ArrayList<LocationDTO>();
		for(LocationRecord i:locations) {
			toReturn.add(new LocationDTO(i));
		}



		return ResponseEntity
				.status(HttpStatus.OK)
				.body(toReturn);






	}
}
