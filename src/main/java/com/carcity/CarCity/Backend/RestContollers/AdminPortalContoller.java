package com.carcity.CarCity.Backend.RestContollers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.carcity.CarCity.Backend.dataentities.*;
import com.carcity.CarCity.Backend.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.carcity.CarCity.Backend.PushNotificationUtil;

@RestController
public class AdminPortalContoller {

	@Autowired ApplicationUserRepo objApplicationUserRepo;
	@Autowired LocationRecordRepo objLocationRecordRepo;
	@Autowired JobsRepo objJobsRepo;
	@Autowired PushNotificationUtil objPushNotificationUtil;

	@Autowired
	ApplicationUserSettingsRepo objApplicationUserSettingsRepo;

	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/AdminPortal/updateProfileInfo"} )
	public ResponseEntity<?> updateProfileInfo(@RequestHeader String sessiontoken,
											   @RequestBody UserSettingInDO obj) throws ParseException {
		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.AdminPortal) {

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


	@RequestMapping(method=RequestMethod.POST,value={"/Authenticated/AdminPortal/getProfileInfo"} )
	public ResponseEntity<?> getProfileInfo(@RequestHeader String sessiontoken) throws ParseException {
		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.AdminPortal) {

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
			JobDTO toAdd=new JobDTO(i);
			toAdd.setCreatedby(""+objApplicationUserSettingsRepo.findAllByUserAndSettingname(i.getCreatedby(),"Your Name")
					+" ("+i.getCreatedby().getCell()+")");
			if(i.getManagedby()!=null) {
				toAdd.setManagedby(""+objApplicationUserSettingsRepo.findAllByUserAndSettingname(i.getManagedby(),"Your Name")
						+" ("+i.getManagedby().getCell()+")");
			} else {
				toAdd.setManagedby("Not managed by anyone yet.");
			}
			toReturn.add(toAdd);
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
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.AdminPortal) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Cannot call this api for "+apu.getUt().toString()));
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
			long cell,
													   @RequestParam String starttime,
													   @RequestParam String endtime) throws Exception {
		try {
			Date ss=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a").parse(starttime);
			Date ee=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a").parse(endtime);
		} catch (Exception ex){
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("DATE/TIME FORMAT ERROR"));
		}


		ApplicationUser apu = objApplicationUserRepo.findBySessiontoken(sessiontoken);

		if(apu==null) {
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("Wrong sessiontoken"));
		} else {

			if(apu.getUt()==UserTypes.AdminPortal) {

			} else {
				return ResponseEntity
						.status(HttpStatus.METHOD_FAILURE)
						.body(new MessageResponce("Cannot call this api for "+apu.getUt().toString()));
			}


		}

		ApplicationUser sp=objApplicationUserRepo.findByCell(cell);

		if(sp==null){
			return ResponseEntity
					.status(HttpStatus.METHOD_FAILURE)
					.body(new MessageResponce("cell no not found"));
		}

		List<LocationRecord> locations = objLocationRecordRepo.findAllByOf(sp);


		List<LocationDTO> toReturn = new ArrayList<LocationDTO>();
		for(LocationRecord i:locations) {
			Date ss=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a").parse(starttime);
			Date ee=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a").parse(endtime);

			if(i.getTimeondevice().after(ss) && i.getTimeondevice().before(ee)){
				toReturn.add(new LocationDTO(i));
			}


		}



		return ResponseEntity
				.status(HttpStatus.OK)
				.body(toReturn);






	}
}
