package com.vadrin.mmtstrain.mmtsservices;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vadrin.mmtstrain.mmtsservices.dto.TrainInfo;
import com.vadrin.mmtstrain.utils.Util;

@Component
public class TrainScheduleService {

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@Autowired
	StationNameToIDService stationNameToIDService;

	private static final String baseURL = "http://www.mmtstraintimings.in/MMTSTimingService.svc/trainsbetween/";
	private RestTemplate restTemplate;

	private TrainInfo[] getSchedule(String fromId, String toId, String startTime, String endTime) {
		restTemplate = restTemplateBuilder.build();
		String url = baseURL + fromId + "/" + toId + "/" + startTime + "/" + endTime;
		return restTemplate.getForObject(url, TrainInfo[].class);
	}

	public String getResponseString(String from, String to, String time) throws ParseException {
		String fromId = stationNameToIDService.getID(from);
		String toId = stationNameToIDService.getID(to);
		TrainInfo[] allSchedules = getSchedule(fromId, toId, Util.increaseHours(time, -1), Util.increaseHours(time, 1));
		if (allSchedules.length > 1) {
			return "Ok. I found a mmts from " + allSchedules[0].getStartstation() + " to "
					+ allSchedules[0].getStopstation() + " at " + allSchedules[0].getStarttime()
					+ ". The next one is at " + allSchedules[1].getStarttime() + ".";
		} else if (allSchedules.length > 0) {
			return "Ok. I found a mmts from " + allSchedules[0].getStartstation() + " to "
					+ allSchedules[0].getStopstation() + " at " + allSchedules[0].getStarttime() + ".";
		} else {
			return "Unfortunately. I dont see any trains around the time you have mentioned.";
		}
	}
	
	public String getResponseString(String from, String to) throws ParseException {
		String fromId = stationNameToIDService.getID(from);
		String toId = stationNameToIDService.getID(to);
		TrainInfo[] allSchedules = getSchedule(fromId, toId, "0000", "2359");
		if (allSchedules.length > 1) {
			return "Ok. I found a mmts from " + allSchedules[0].getStartstation() + " to "
					+ allSchedules[0].getStopstation() + " at " + allSchedules[0].getStarttime()
					+ " today. The next one is at " + allSchedules[1].getStarttime() + ".";
		} else if (allSchedules.length > 0) {
			return "Ok. I found a mmts from " + allSchedules[0].getStartstation() + " to "
					+ allSchedules[0].getStopstation() + " at " + allSchedules[0].getStarttime() + ".";
		} else {
			return "Unfortunately. I dont see any trains for the stations you have mentioned.";
		}
	}

}
