package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.googlehome.GoogleResponse;
import com.vadrin.mmtstrain.services.GoogleHomeService;

@RestController
public class GoogleHomeCallbackController {

	@Autowired
	MmtsTrainController mmtsTrainController;

	@Autowired
	GoogleHomeService googleHomeService;

	@RequestMapping(value = { "/callback/google" }, method = { RequestMethod.POST })
	public GoogleResponse getChat(@RequestBody JsonNode requestBody) {
		System.out.println("request is - " + requestBody.toString());
		if (requestBody.get("result").get("metadata").has("intentName")) {
			Chat input = new Chat(requestBody.get("result").get("parameters").get("userInput").asText());
			Chat output = mmtsTrainController.converse("123", input);
			return googleHomeService.constructGoogleResponse(output);
		}else {
			return googleHomeService.constructGoogleResponse("NEEDS DEVELOPMENT", false);
		}
	}

}