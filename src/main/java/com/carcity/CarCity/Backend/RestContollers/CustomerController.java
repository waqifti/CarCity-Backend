package com.carcity.CarCity.Backend.RestContollers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
import com.carcity.CarCity.Backend.dataentities.JobState;
import com.carcity.CarCity.Backend.dataentities.Jobs;
import com.carcity.CarCity.Backend.dataentities.JobsRepo;
import com.carcity.CarCity.Backend.dataentities.LocationProvider;
import com.carcity.CarCity.Backend.dataentities.LocationRecord;
import com.carcity.CarCity.Backend.dataentities.LocationRecordRepo;
import com.carcity.CarCity.Backend.dataentities.UserTypes;
import com.carcity.CarCity.Backend.dtos.JobDTO;

@RestController
public class CustomerController {
	@Autowired ApplicationUserRepo objApplicationUserRepo;
	@Autowired LocationRecordRepo objLocationRecordRepo;
	@Autowired JobsRepo objJobsRepo;
	
	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/Customer/getJobDetails"} )
	public ResponseEntity<?> createJobRequest(@RequestHeader String sessiontoken,
			@RequestParam Integer jobid) throws ParseException {

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


		Jobs job = objJobsRepo.getById(jobid);
		
		if(job!=null) {
			if(job.getCreatedby().getId()==apu.getId()) {
				return ResponseEntity
						.status(HttpStatus.OK)
						.body(new JobDTO(job));
			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body("Not your Job.");
			}
		} else {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body("Job not found.");
		}
		
	
	}

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/Customer/createJobRequest"} )
	public ResponseEntity<?> createJobRequest(@RequestHeader String sessiontoken,
			@RequestParam HashSet<String> jobtypes,
			@RequestParam String description,
			@RequestParam String notes,
			@RequestParam(required=false) String scheduledAt) throws ParseException {

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


		Jobs newJob = new Jobs();
		newJob.setCreatedby(apu);
		newJob.setJobtypes(jobtypes);
		newJob.setDescription(description);
		newJob.setNotes(notes);


		if(scheduledAt!=null) {
			Date date1=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a").parse(scheduledAt); 
			newJob.setScheduledAt(date1);
			newJob.setState(JobState.NEW_JOB_SCHEDULED_LATER);
		} else {
			newJob.setState(JobState.NEW_JOB_WANTS_SERVICE_NOW);
		}

		List<ApplicationUser> sps= objApplicationUserRepo.findAllByUt(UserTypes.AdminPortal);
		int randomNum = ThreadLocalRandom.current().nextInt(0, sps.size());
		newJob.setManagedby(sps.get(randomNum));





		newJob=objJobsRepo.saveAndFlush(newJob);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(newJob.getId());
	}

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/Customer/UpdateLocation"} )
	public ResponseEntity<?> UpdateCustomerLocation(@RequestHeader String sessiontoken,
			@RequestParam double lati,
			@RequestParam double longi,
			@RequestParam String time,
			@RequestParam AppState appstate,
			@RequestParam LocationProvider locationprovider) throws Exception {



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
		//Sep 7, 2021 6:01:18 PM
		try {
			Date date1=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a").parse(time);  



			LocationRecord toSave=new LocationRecord();
			toSave.setLati(lati);
			toSave.setLongi(longi);
			toSave.setTimeondevice(date1);
			toSave.setAppstate(appstate);
			toSave.setOf(apu);
			toSave.setLocationprovider(locationprovider);

			objLocationRecordRepo.saveAndFlush(toSave);


			return ResponseEntity
					.status(HttpStatus.OK)
					.body("");
		} catch (Exception ex) {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(ex);
		}








	}

}
