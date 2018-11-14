package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.alexa.AlexaResponse;
import com.vadrin.mmtstrain.services.AlexaServices;

@RestController
public class AlexaCallbackController {

	@Autowired
	MmtsTrainController mmtsTrainController;

	@Autowired
	AlexaServices alexaServices;

	@RequestMapping(value = { "/callback/alexa/findTrain" }, method = { RequestMethod.POST })
	public AlexaResponse getChat(@RequestBody JsonNode requestBody) {
		System.out.println("request is - " + requestBody.toString());
		if (requestBody.get("request").get("type").asText().equalsIgnoreCase("IntentRequest")
				&& requestBody.get("request").get("intent").get("slots").get("userInput").has("value")) {
			Chat output = mmtsTrainController.converse("123", new Chat(
					requestBody.get("request").get("intent").get("slots").get("userInput").get("value").asText()));
			return alexaServices.constructAlexaResponse(output.getMessage(), output.isTheEnd());
		} else {
			return alexaServices.constructAlexaResponse("NEEDS DEVELOPMENT", false);
		}
	}

}