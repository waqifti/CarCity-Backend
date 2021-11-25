package com.carcity.CarCity.Backend.RestContollers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.carcity.CarCity.Backend.dataentities.*;
import com.carcity.CarCity.Backend.dtos.MessageResponce;
import com.carcity.CarCity.Backend.dtos.UserSettingPTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.carcity.CarCity.Backend.dtos.JobDTO;
import com.carcity.CarCity.Backend.dtos.ServiceProviderUserDTO;

@RestController
public class ServiceProviderController {
	Logger logger = LoggerFactory.getLogger(ServiceProviderController.class);
	@Autowired ApplicationUserRepo objApplicationUserRepo;
	@Autowired LocationRecordRepo objLocationRecordRepo;
	@Autowired JobsRepo objJobsRepo;

	@Autowired
	ApplicationUserSettingsRepo objApplicationUserSettingsRepo;

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/ServiceProvider/updateProfileInfo"} )
	public ResponseEntity<?> updateProfileInfo(@RequestHeader String sessiontoken,
											   @RequestBody List<UserSettingPTO> settings) throws ParseException {
		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.ServiceProvider) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Cannot call this api for "+apu.getUt().toString()));
			}


		}

		if(settings!=null && settings.size()>0){
			for(UserSettingPTO i:settings){
				ApplicationUserSettings objApplicationUserSettings=objApplicationUserSettingsRepo
						.findAllByUserAndSettingname(apu,i.getSettingname());

				if(objApplicationUserSettings==null){
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


	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/ServiceProvider/getProfileInfo"} )
	public ResponseEntity<?> getProfileInfo(@RequestHeader String sessiontoken) throws ParseException {
		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.ServiceProvider) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Cannot call this api for "+apu.getUt().toString()));
			}


		}

		UserSettingPTO set1=new UserSettingPTO();
		set1.setSettingname("Managed By");
		for(ApplicationUser aii:objApplicationUserRepo.findAllByUt(UserTypes.AdminPortal)){
			set1.getSelectablevalues().add(aii.getCell()+"");
		}

		UserSettingPTO set2=new UserSettingPTO();
		set2.setSettingname("Your Name");


		List<UserSettingPTO> settings=new ArrayList<UserSettingPTO>();
		settings.add(set1);
		settings.add(set2);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(settings);

	}

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
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.ServiceProvider) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Cannot call this api for "+apu.getUt().toString()));
			}


		}

		//Sep 7, 2021 6:01:18 PM
		//18 Sep 2021 14:24:53

		LocationRecord toSave=new LocationRecord();


		try {
			Date date1=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a").parse(time);  
			toSave.setTimeondevice(date1);

		} catch (Exception ex) {
			try {
				Date date1=new SimpleDateFormat("dd MMM yyyy HH:mm:ss").parse(time); 
				toSave.setTimeondevice(date1);
			} catch (Exception ex2) {
				toSave.setTimeondeviceasstring(time);
				logger.error("SS2",ex2);

			}
			logger.error("SS1",ex);





		}








		toSave.setLati(lati);
		toSave.setLongi(longi);

		toSave.setOf(apu);
		toSave.setAppstate(appstate);
		toSave.setLocationprovider(locationprovider);

		objLocationRecordRepo.saveAndFlush(toSave);


		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new MessageResponce("ok"));






	}

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/ServiceProvider/activateJobSearch"} )
	public ResponseEntity<?> activatejobsearch(@RequestHeader String sessiontoken) throws ParseException {

		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.ServiceProvider) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Cannot call this api for "+apu.getUt().toString()));
			}


		}


		apu.setJobsearchactivated(true);
		objApplicationUserRepo.saveAndFlush(apu);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new MessageResponce("Done"));
	}

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/ServiceProvider/deactivateJobSearch"} )
	public ResponseEntity<?> deactivateJobSearch(@RequestHeader String sessiontoken) throws ParseException {

		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.ServiceProvider) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Cannot call this api for "+apu.getUt().toString()));
			}


		}


		apu.setJobsearchactivated(false);
		objApplicationUserRepo.saveAndFlush(apu);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new MessageResponce("Done"));
	}


	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/ServiceProvider/getAssignedJobDetails"} )
	public ResponseEntity<?> getAssignedJobDetails(@RequestHeader String sessiontoken) throws ParseException {

		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.ServiceProvider) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Cannot call this api for "+apu.getUt().toString()));
			}


		}


		Jobs job = objJobsRepo.findByAssignedto(apu);

		if(job!=null) {
			JobDTO toSend= new JobDTO(job);
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
					.body(new MessageResponce("Job not found."));
		}


	}

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/ServiceProvider/completeAssignedJob"} )
	public ResponseEntity<?> completeAssignedJob(@RequestHeader String sessiontoken) throws ParseException {

		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.ServiceProvider) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Cannot call this api for "+apu.getUt().toString()));
			}


		}
		apu.setJobsearchactivated(false);
		objApplicationUserRepo.saveAndFlush(apu);

		Jobs job = objJobsRepo.findByAssignedto(apu);

		if(job!=null) {

			job.setAssignedto(null);
			job.setCompletedby(apu);
			objJobsRepo.saveAndFlush(job);

			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new MessageResponce("Done"));

		} else {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Job not found."));
		}



	}
}
