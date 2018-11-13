package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Train;
import com.vadrin.mmtstrain.services.DialogflowServices;
import com.vadrin.mmtstrain.services.TrainScheduleService;
import com.vadrin.mmtstrain.utils.Util;

@RestController
public class MmtsTrainController {

	@Autowired
	TrainScheduleService trainScheduleService;
	@Autowired
	DialogflowServices dialogflowServices;

	@RequestMapping(value = { "/train" }, method = { RequestMethod.GET })
	public Train[] getTrain(@RequestParam("requestQuery") String requestQuery) {
		Train[] toReturn;
		System.out.println(requestQuery);
		JsonNode dialogflowResponse = dialogflowServices.getIntentsAndEntitiesFromText(requestQuery);
		if (dialogflowResponse.get("result").get("metadata").get("intentName").asText().equalsIgnoreCase("findTrain")) {
			String from = dialogflowResponse.get("result").get("parameters").get("from").asText();
			String to = dialogflowResponse.get("result").get("parameters").get("to").asText();
			String time = dialogflowResponse.get("result").get("parameters").get("time").asText();
			toReturn = trainScheduleService.getSchedule(from, to, time);
			System.out.println(Util.formatScheduleInEnglish(toReturn));
		} else {
			toReturn = new Train[0];
		}
		return toReturn;
	}

}