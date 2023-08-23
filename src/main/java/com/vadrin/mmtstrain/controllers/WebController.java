package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vadrin.mmtstrain.models.InvalidStationNamesException;
import com.vadrin.mmtstrain.services.EventsHandlerService;
import com.vadrin.mmtstrain.services.TrainScheduleService;

@RestController
public class WebController {

	@Autowired
	TrainScheduleService trainScheduleService;
	
	@Autowired
	EventsHandlerService eventsHandlerService;

	@GetMapping("/trains")
	public String callback(@RequestParam String from, @RequestParam String to, @RequestParam String time) throws InvalidStationNamesException {
		return eventsHandlerService.formatScheduleInEnglish(trainScheduleService.getSchedule(from, to, time));
	}

}