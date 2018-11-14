package com.vadrin.mmtstrain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.Train;
import com.vadrin.mmtstrain.utils.Util;

@Service
public class DialogflowResponseHandlerService {
	
	@Autowired
	TrainScheduleService trainScheduleService;
	
	private static final String TROUBLE_UNDERSTANDING = "I am having trouble understanding you. Please try later.";

	public Chat handle(JsonNode dialogflowResponse) {
		if (dialogflowResponse.get("result").has("speech")
				&& !dialogflowResponse.get("result").get("speech").asText().equalsIgnoreCase("")) {
			return new Chat(dialogflowResponse.get("result").get("speech").asText(), false);
		} else if (dialogflowResponse.get("result").get("metadata").get("intentName").asText()
				.equalsIgnoreCase("findTrain")) {
			String from = dialogflowResponse.get("result").get("parameters").get("from").asText();
			String to = dialogflowResponse.get("result").get("parameters").get("to").asText();
			String time = dialogflowResponse.get("result").get("parameters").get("time").asText();
			Train[] trains = trainScheduleService.getSchedule(from, to, time);
			return new Chat(Util.formatScheduleInEnglish(trains), true);
		} else {
			return new Chat(TROUBLE_UNDERSTANDING, true);
		}
	}
	
}
