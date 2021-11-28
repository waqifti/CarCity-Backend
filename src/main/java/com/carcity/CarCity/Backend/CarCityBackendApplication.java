package com.carcity.CarCity.Backend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
	
	@PostConstruct
	public void fixed(){


		//LocationRecord latestLocation=objLocationRecordRepo.findTopByOfOrderByTimeondeviceDesc(objApplicationUserRepo.findByCell(3238867429L));
		
		
		//System.out.println(latestLocation);
	}
	



	public static void main(String[] args) throws ParseException {
		
		
		//
		
	   // Date date1=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a").parse("Sep 7, 2021 6:01:18 PM");  
		
		SpringApplication.run(CarCityBackendApplication.class, args);
	}

}
