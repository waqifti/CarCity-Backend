package com.carcity.CarCity.Backend.RestContollers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.carcity.CarCity.Backend.dtos.MessageResponce;
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
import com.carcity.CarCity.Backend.dtos.ServiceProviderUserDTO;

@RestController
public class CustomerController {
	@Autowired ApplicationUserRepo objApplicationUserRepo;
	@Autowired LocationRecordRepo objLocationRecordRepo;
	@Autowired JobsRepo objJobsRepo;
	

			

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/Customer/getJobDetails"} )
	public ResponseEntity<?> getJobDetails(@RequestHeader String sessiontoken,
			@RequestParam Integer jobid) throws ParseException {

		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.Customer) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Cannot call this api for "+apu.getUt().toString()));
			}


		}


		Jobs job = objJobsRepo.getById(jobid);

		if(job!=null) {
			if(job.getCreatedby().getId()==apu.getId()) {


				JobDTO toSend=new JobDTO(job);

				if(job.getAssignedto()!=null) {
					ServiceProviderUserDTO toAdd=new ServiceProviderUserDTO();
					toAdd.setCell(job.getAssignedto().getCell());

					LocationRecord latestLocation=objLocationRecordRepo.findTopByOfOrderByTimeondeviceDesc(job.getAssignedto());
					if(latestLocation!=null) {
						toAdd.setCurrentlati(latestLocation.getLati());
						toAdd.setCurrentlongi(latestLocation.getLongi());
					}



					toSend.setAssignedtodetails(toAdd);		
					toSend.setAssignedto(""+job.getAssignedto().getName()+" ("+job.getAssignedto().getCell()+")");




				} 
				return ResponseEntity
						.status(HttpStatus.OK)
						.body(toSend);
			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Not your Job."));
			}
		} else {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Job not found."));
		}


	}

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/Customer/createJobRequest"} )
	public ResponseEntity<?> createJobRequest(@RequestHeader String sessiontoken,
			@RequestParam HashSet<String> jobtypes,
			@RequestParam String description,
			@RequestParam String notes,
			@RequestParam double lati,
			@RequestParam double longi,
			@RequestParam(required=false) String scheduledAt) throws ParseException {

		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.Customer) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Cannot call this api for "+apu.getUt().toString()));
			}


		}


		Jobs newJob = new Jobs();
		newJob.setCreatedby(apu);
		newJob.setJobtypes(jobtypes);
		newJob.setDescription(description);
		newJob.setNotes(notes);
		newJob.setLati(lati);
		newJob.setLongi(longi);


		if(scheduledAt!=null) {
			try {
				Date date1=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a").parse(scheduledAt); 
				newJob.setScheduledAt(date1);
			} catch (Exception ex) {

				Date date1=new SimpleDateFormat("dd MMM yyyy HH:mm:ss").parse(scheduledAt); 
				newJob.setScheduledAt(date1);





			}

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
				.body(new MessageResponce(newJob.getId()+""));
	}


}
