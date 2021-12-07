package com.carcity.CarCity.Backend.RestContollers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.carcity.CarCity.Backend.PushNotificationUtil;
import com.carcity.CarCity.Backend.dataentities.*;
import com.carcity.CarCity.Backend.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
	@Autowired ApplicationUserRepo objApplicationUserRepo;
	@Autowired LocationRecordRepo objLocationRecordRepo;
	@Autowired JobsRepo objJobsRepo;

	@Autowired
	ApplicationUserSettingsRepo objApplicationUserSettingsRepo;

	@Autowired
	PushNotificationUtil objPushNotificationUtil;



	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/Customer/updateProfileInfo"} )
	public ResponseEntity<?> updateProfileInfo(@RequestHeader String sessiontoken,
											   @RequestBody UserSettingInDO obj) throws ParseException {
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
		if(obj!=null &&  obj.getSettings()!=null && obj.getSettings().size()>0){
			for(UserSettingPTO i:obj.getSettings()){
				ApplicationUserSettings objApplicationUserSettings=objApplicationUserSettingsRepo
						.findAllByUserAndSettingname(apu,i.getSettingname());

				if(objApplicationUserSettings==null){
					objApplicationUserSettings = new ApplicationUserSettings();
					objApplicationUserSettings.setSettingname(i.getSettingname());
					objApplicationUserSettings.setUser(apu);
				}

				objApplicationUserSettings.setSettingvalue(i.getSelectedvalue());
				objApplicationUserSettingsRepo.saveAndFlush(objApplicationUserSettings);
			}
		}

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new MessageResponce("Done"));

	}


	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/Customer/getProfileInfo"} )
	public ResponseEntity<?> getProfileInfo(@RequestHeader String sessiontoken) throws ParseException {
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

		List<ApplicationUserSettings> saveSettings=objApplicationUserSettingsRepo.findAllByUser(apu);

		List<UserSettingPTO> settings=new ArrayList<UserSettingPTO>();

		if(saveSettings==null || saveSettings.size()==0){


			UserSettingPTO set2=new UserSettingPTO();
			set2.setSettingname("Your Name");




			settings.add(set2);
		} else {
			for(ApplicationUserSettings vr:saveSettings){
				UserSettingPTO set1=new UserSettingPTO();
				set1.setSettingname(vr.getSettingname());
				set1.setSelectedvalue(vr.getSettingvalue());


				settings.add(set1);
			}
		}



		return ResponseEntity
				.status(HttpStatus.OK)
				.body(settings);

	}

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/Customer/cancelAllMyJobs"} )
	public ResponseEntity<?> cancelAllMyJobs(@RequestHeader String sessiontoken) throws ParseException {
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


		List<Jobs> jobs = objJobsRepo.findAllByCreatedbyAndStateIn(apu,
				Arrays.asList(JobState.NEW_JOB_SCHEDULED_LATER,
				JobState.NEW_JOB_WANTS_SERVICE_NOW,
				JobState.JOB_ASSIGNED_TO_SP,
						JobState.CANCEL_BY_CUSTOMER));

		if(jobs!=null){
			for(Jobs j:jobs){
				j.setState(JobState.CANCEL_BY_CUSTOMER);
				j.setAssignedto(null);
				objJobsRepo.saveAndFlush(j);
			}
		}


		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new MessageResponce("Done"));

	}

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/Customer/getJobDetails"} )
	public ResponseEntity<?> getJobDetails(@RequestHeader String sessiontoken) throws ParseException {

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


		Jobs job = objJobsRepo.findByCreatedbyAndStateIn(apu,
				Arrays.asList(JobState.NEW_JOB_SCHEDULED_LATER,
				JobState.NEW_JOB_WANTS_SERVICE_NOW,
				JobState.JOB_ASSIGNED_TO_SP));

		if(job!=null){
			JobDTO toSend=new JobDTO(job);

			toSend.setCreatedby(""+objApplicationUserSettingsRepo.findAllByUserAndSettingname(job.getCreatedby(),"Your Name")
					+" ("+job.getCreatedby().getCell()+")");


			if(job.getManagedby()!=null) {
				toSend.setManagedby(""+objApplicationUserSettingsRepo.findAllByUserAndSettingname(job.getManagedby(),"Your Name")
						+" ("+job.getManagedby().getCell()+")");
			} else {
				toSend.setManagedby("Not managed by anyone yet.");
			}

			if(job.getAssignedto()!=null) {
				ServiceProviderUserDTO toAdd=new ServiceProviderUserDTO();
				toAdd.setCell(job.getAssignedto().getCell());

				LocationRecord latestLocation=objLocationRecordRepo.
						findTopByOfOrderByTimeondeviceDesc(job.getAssignedto());
				if(latestLocation!=null) {
					toAdd.setCurrentlati(latestLocation.getLati());
					toAdd.setCurrentlongi(latestLocation.getLongi());
				}



				toSend.setAssignedtodetails(toAdd);
				toSend.setAssignedto(""+objApplicationUserSettingsRepo.findAllByUserAndSettingname(job.getAssignedto(),"Your Name")+" ("+job.getAssignedto().getCell()+")");




			}
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(toSend);
		} else {
			return ResponseEntity
					.status(HttpStatus.PRECONDITION_FAILED)
					.body(new MessageResponce("Job not found (001)."));
		}







	}

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/Customer/createJobRequest"} )
	public ResponseEntity<?> createJobRequest(@RequestHeader String sessiontoken,
			@RequestParam HashSet<String> jobtypes,
			@RequestParam double lati,
			@RequestParam double longi
			) throws ParseException {

		String scheduledAt = null;

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

		Jobs job = objJobsRepo.findByCreatedbyAndStateIn(apu,
				Arrays.asList(JobState.NEW_JOB_SCHEDULED_LATER,
				JobState.NEW_JOB_WANTS_SERVICE_NOW,
				JobState.JOB_ASSIGNED_TO_SP));

		if(job!=null){
			return ResponseEntity
					.status(HttpStatus.PRECONDITION_FAILED)
					.body(new MessageResponce("You already have a job created or assigned to sp."));
		}


		Jobs newJob = new Jobs();
		newJob.setCreatedby(apu);
		newJob.setJobtypes(jobtypes);

		//newJob.setNotes(notes);
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

		if(apu.getFcmtoken()!=null && !apu.getFcmtoken().trim().isEmpty()) {
			try {
				objPushNotificationUtil.sendNotificationToAndroid(apu.getFcmtoken(),
						"Car City",
						"Aap ko guzarish mossol ho gaye hay juld aap ko sp assign ho jaye ga.");
			} catch (Exception ex) {

			}

		}

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new MessageResponce("Done"));
	}


}
