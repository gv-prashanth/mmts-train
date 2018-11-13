package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Train;
import com.vadrin.mmtstrain.models.googlehome.GoogleResponse;
import com.vadrin.mmtstrain.services.GoogleHomeServices;
import com.vadrin.mmtstrain.utils.Util;

@RestController
public class GoogleHomeCallbackController {

	@Autowired
	MmtsTrainController mmtsTrainController;

	@Autowired
	GoogleHomeServices googleHomeServices;

	private static final String DIDNT_UNDERSTAND_RESPONSE = "I didnt quite understand you. Please try asking me differently.";

	@RequestMapping(value = { "/callback/google/findTrain" }, method = { RequestMethod.POST })
	public GoogleResponse getChat(@RequestBody JsonNode requestBody) {
		System.out.println("request is - " + requestBody.toString());
		if (requestBody.has("result") && requestBody.get("result").has("metadata")  && requestBody.get("result").get("metadata").has("intentName")) {
			String intentName = requestBody.get("result").get("metadata").get("intentName").asText();
			if(intentName.equalsIgnoreCase("findMMTS")){
				JsonNode slots = requestBody.get("result").get("parameters");
				if (slots.has("userInput")) {
					String userInput = slots.get("userInput").asText();
					Train[] trains = mmtsTrainController.getTrain(userInput);
					return googleHomeServices.constructGoogleResponse(Util.formatScheduleInEnglish(trains), true);
				}
			}
		}
		return googleHomeServices.constructGoogleResponse(DIDNT_UNDERSTAND_RESPONSE, false);
	}

}