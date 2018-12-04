package com.vadrin.mmtstrain.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.Event;
import com.vadrin.mmtstrain.models.Train;
import com.vadrin.mmtstrain.services.TrainScheduleService;

@RestController
public class MmtsTrainController {

	@Autowired
	TrainScheduleService trainScheduleService;

	private static final String TROUBLE_UNDERSTANDING = "I am having trouble understanding you. Please try later.";
	private static final String GREET = "Hello! I can help you find a MMTS train.";

	@RequestMapping(value = { "/conversation/id" }, method = { RequestMethod.GET })
	public String createConversationId() {
		return UUID.randomUUID().toString();
	}

	@RequestMapping(value = { "/conversation/{conversationId}/event" }, method = { RequestMethod.PUT })
	public Chat converse(@PathVariable("conversationId") String conversationId, @RequestBody Event event) {
		switch (event.getName()) {
		case "LaunchRequest":
			return new Chat(GREET);
		case "findTrain":
			return new Chat(formatScheduleInEnglish(trainScheduleService.getSchedule(event.getInfo().get("from"),
					event.getInfo().get("to"), event.getInfo().get("time"))), true);
		default:
			return new Chat(TROUBLE_UNDERSTANDING, true);
		}
	}

	private String formatScheduleInEnglish(Train[] allSchedules) {
		if (allSchedules.length > 1) {
			return "Ok. There are " + allSchedules.length + " trains. First mmts from "
					+ allSchedules[0].getStartstation() + " to " + allSchedules[0].getStopstation() + " is at "
					+ allSchedules[0].getStarttime() + ". The next one is at " + allSchedules[1].getStarttime() + ".";
		} else if (allSchedules.length > 0) {
			return "Ok. I found a mmts from " + allSchedules[0].getStartstation() + " to "
					+ allSchedules[0].getStopstation() + " at " + allSchedules[0].getStarttime() + ".";
		} else {
			return "Unfortunately. I dont see any trains around the time you have mentioned.";
		}
	}

}