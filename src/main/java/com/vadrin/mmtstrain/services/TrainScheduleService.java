package com.vadrin.mmtstrain.services;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vadrin.mmtstrain.models.Train;
import com.vadrin.mmtstrain.utils.Util;

@Component
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

	public Train[] getSchedule(String from, String to, String time) throws ParseException {
		String fromId = stationNameToIDService.getID(from);
		String toId = stationNameToIDService.getID(to);
		return getSchedule(fromId, toId, Util.increaseHours(time, -1), Util.increaseHours(time, 1));
	}

}
