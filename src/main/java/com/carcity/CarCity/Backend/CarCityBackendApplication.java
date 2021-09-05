package com.carcity.CarCity.Backend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.carcity.CarCity.Backend.dataentities.ApplicationUser;
import com.carcity.CarCity.Backend.dataentities.ApplicationUserRepo;
import com.carcity.CarCity.Backend.dataentities.JobState;
import com.carcity.CarCity.Backend.dataentities.Jobs;
import com.carcity.CarCity.Backend.dataentities.JobsRepo;
import com.carcity.CarCity.Backend.dataentities.LocationRecord;
import com.carcity.CarCity.Backend.dataentities.LocationRecordRepo;
import com.carcity.CarCity.Backend.dataentities.UserTypes;

@SpringBootApplication
public class CarCityBackendApplication {

	@Autowired ApplicationUserRepo objApplicationUserRepo;
	@Autowired LocationRecordRepo objLocationRecordRepo;
	@Autowired JobsRepo objJobsRepo;

	public static List<Double> getLocation(int radiusinkm) {
		//Iftekhar House

		int radius=radiusinkm*1000;
		double x0=74.390665; // long
		double y0=31.477025; // lat

		Random random = new Random();

		// Convert radius from meters to degrees
		double radiusInDegrees = radius / 111000f;

		double u = random.nextDouble();
		double v = random.nextDouble();
		double w = radiusInDegrees * Math.sqrt(u);
		double t = 2 * Math.PI * v;
		double x = w * Math.cos(t);
		double y = w * Math.sin(t);

		// Adjust the x-coordinate for the shrinking of the east-west distances
		double new_x = x / Math.cos(Math.toRadians(y0));

		double foundLongitude = new_x + x0;
		double foundLatitude = y + y0;

		List<Double> toReturn = new ArrayList<Double>();
		toReturn.add(foundLongitude);
		toReturn.add(foundLatitude);

		return toReturn;


		//System.out.println("Longitude: " + foundLongitude + "  Latitude: " + foundLatitude );
	}

	public Date addHoursToJavaUtilDate(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();
	}

	//@PostConstruct
	public void initjobs(){
		List<ApplicationUser> sps= objApplicationUserRepo.findAllByUt(UserTypes.ServiceProvider);
		for(int i=0;i<10;i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, sps.size());

			Jobs toSave=new Jobs();
			toSave.setAssignedto(sps.get(randomNum));
			toSave.setState(JobState.randomState());
			toSave.setDescription("Short details");
			toSave.setNotes("Long details Long details Long details Long details Long details Long details Long details Long details Long details Long details");
			objJobsRepo.saveAndFlush(toSave);

		}

	}

	public void initlocations(){
		System.out.println("OK Start");

		List<ApplicationUser> sps= objApplicationUserRepo.findAllByUt(UserTypes.ServiceProvider);

		for(int i=0;i<100;i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, sps.size());

			LocationRecord toSave=new LocationRecord();

			List<Double> locR=getLocation(25);

			toSave.setLongi(locR.get(0));
			toSave.setLati(locR.get(1));



			int hours = ThreadLocalRandom.current().nextInt(0, 72);

			toSave.setTimeondevice(addHoursToJavaUtilDate(new Date(),-1*hours ));

			toSave.setOf(sps.get(randomNum));

			objLocationRecordRepo.saveAndFlush(toSave);
		}




		System.out.println("OK End");
	}

	public static void main(String[] args) {
		SpringApplication.run(CarCityBackendApplication.class, args);
	}

}
