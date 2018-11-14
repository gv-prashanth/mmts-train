package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.Event;
import com.vadrin.mmtstrain.models.alexa.AlexaResponse;
import com.vadrin.mmtstrain.services.AlexaService;

@RestController
public class AlexaCallbackController {

	@Autowired
	MmtsTrainController mmtsTrainController;

	@Autowired
	AlexaService alexaService;

	@RequestMapping(value = { "/callback/alexa" }, method = { RequestMethod.POST })
	public AlexaResponse getChat(@RequestBody JsonNode requestBody) {
		System.out.println("request is - " + requestBody.toString());
		if (requestBody.get("request").get("type").asText().equalsIgnoreCase("IntentRequest")) {
			Chat input = new Chat(
					requestBody.get("request").get("intent").get("slots").get("userInput").get("value").asText());
			Chat output = mmtsTrainController.converse(requestBody.get("session").get("sessionId").asText(), input);
			return alexaService.constructAlexaResponse(output);
		} else {
			Event input = new Event(requestBody.get("request").get("type").asText());
			Chat output = mmtsTrainController.converse(requestBody.get("session").get("sessionId").asText(), input);
			return alexaService.constructAlexaResponse(output);
		}
	}

}