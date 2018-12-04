package com.vadrin.mmtstrain.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vadrin.mmtstrain.exceptions.InvalidStationNamesException;
import com.vadrin.mmtstrain.models.Train;

@Service
public class TrainScheduleService {

	@Autowired
	RestTemplateBuilder restTemplateBuilder;
	@Autowired
	StationNameToIDService stationNameToIDService;

	private static final String baseURL = "http://www.mmtstraintimings.in/MMTSTimingService.svc/trainsbetween/";
	private RestTemplate restTemplate;

	public Train[] getSchedule(String fromId, String toId, String startTime, String endTime) {
		restTemplate = restTemplateBuilder.build();
		String url = baseURL + fromId + "/" + toId + "/" + startTime + "/" + endTime;
		return restTemplate.getForObject(url, Train[].class);
	}

	public Train[] getSchedule(String from, String to, String time) throws InvalidStationNamesException {
		String fromId = stationNameToIDService.getID(from);
		String toId = stationNameToIDService.getID(to);
		if(fromId==null || toId==null){
			throw new InvalidStationNamesException();
		}
		try {
			return getSchedule(fromId, toId, increaseHours(time, -1), increaseHours(time, 1));
		} catch (ParseException e) {
			return getSchedule(fromId, toId, "01:00", "23:00");
		}
	}
	
	private String increaseHours(String input, int hours) throws ParseException{
		input = input.contains(":") ? input : input+":00";
		DateFormat formatterIn = new SimpleDateFormat("HH:mm");
		Date inputTime = formatterIn.parse(input);
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputTime);
		cal.add(Calendar.HOUR, hours);
		DateFormat formatterOut = new SimpleDateFormat("HHmm");
		return formatterOut.format(cal.getTime());
	}

}
