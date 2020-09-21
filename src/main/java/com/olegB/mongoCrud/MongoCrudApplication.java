package com.olegB.mongoCrud;

import com.olegB.mongoCrud.model.Activity;
import com.olegB.mongoCrud.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class MongoCrudApplication implements CommandLineRunner {

	@Autowired
	ActivityRepository activityRepository;

	public static void main(String[] args) {
		SpringApplication.run(MongoCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		activityRepository.deleteAll();
//		TimeZone tz = TimeZone.getTimeZone("UTC");
//		DateFormat df = new SimpleDateFormat("yyyy-MM-HH:mm"); // Quoted "Z" to indicate UTC, no timezone offset
//		df.setTimeZone(tz);
//		String nowAsISO = df.format(new Date());
//
//		Activity activity = new Activity();
//		activity.setTitle("Title");
//		activity.setSummary("Custom summary");
//		activity.setInfo("Custom info");
//		activity.setDescription("Custom description");
//		activity.setId(1L);
//		activity.setStartDateTime(nowAsISO);
//		activity.setEndDateTime(nowAsISO);
//		activityRepository.insert(activity);

	}
}
