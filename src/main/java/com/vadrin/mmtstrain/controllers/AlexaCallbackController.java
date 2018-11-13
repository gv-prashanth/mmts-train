package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Train;
import com.vadrin.mmtstrain.models.alexa.AlexaResponse;
import com.vadrin.mmtstrain.services.AlexaServices;
import com.vadrin.mmtstrain.utils.Util;

@RestController
public class AlexaCallbackController {
	
	@Autowired
	MmtsTrainController mmtsTrainController;
	
	@Autowired
	AlexaServices alexaServices;
	
	private static final String DIDNT_UNDERSTAND_RESPONSE = "I didnt quite understand you. Please try asking me differently.";

	@RequestMapping(value = { "/callback/alexa/findTrain" }, method = { RequestMethod.POST })
	public AlexaResponse getChat(@RequestBody JsonNode requestBody) {
		System.out.println("request is - " + requestBody.toString());
		String requestType = requestBody.get("request").get("type").asText();
		if (requestType.equalsIgnoreCase("IntentRequest")) {
			JsonNode slots = requestBody.get("request").get("intent").get("slots");
			if (slots.get("userInput").has("value")) {
				String userInput = slots.get("userInput").get("value").asText();
				Train[] trains = mmtsTrainController.getTrain(userInput);
				return alexaServices.constructAlexaResponse(Util.formatScheduleInEnglish(trains), true);
			}
		}
		return alexaServices.constructAlexaResponse(DIDNT_UNDERSTAND_RESPONSE, false);
	}

}