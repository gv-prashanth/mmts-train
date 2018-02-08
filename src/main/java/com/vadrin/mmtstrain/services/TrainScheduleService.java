package com.vadrin.mmtstrain.services;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vadrin.mmtstrain.dto.TrainInfo;
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
		String lowerTime = Util.increaseHours(time, -1);
		String upperTime = Util.increaseHours(time, 1);
		TrainInfo[] allSchedules = getSchedule(fromId, toId, lowerTime, upperTime);
		return "Ok. I found a mmts from " + allSchedules[0].getStartstation() + " to "
				+ allSchedules[0].getStopstation() + " at " + allSchedules[0].getStarttime() + " today.";
	}

}
