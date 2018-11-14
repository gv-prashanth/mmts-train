package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Chat;
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

	private static final String TROUBLE_UNDERSTANDING = "I am having trouble understanding you. Please try later.";

	@RequestMapping(value = { "/conversation/id" }, method = { RequestMethod.GET })
	public String initiateConverasation() {
		return Util.generateRandomId();
	}

	@RequestMapping(value = { "/conversation/{conversationId}/chat" }, method = { RequestMethod.PUT })
	public Chat converse(@PathVariable("conversationId") String conversationId, @RequestBody Chat chat) {
		JsonNode dialogflowResponse = dialogflowServices.getConverationEngineResponse(conversationId,
				chat.getMessage());
		System.out.println(dialogflowResponse);
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