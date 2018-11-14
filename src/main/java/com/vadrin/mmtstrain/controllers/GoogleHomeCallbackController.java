package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.googlehome.GoogleResponse;
import com.vadrin.mmtstrain.services.GoogleHomeServices;

@RestController
public class GoogleHomeCallbackController {

	@Autowired
	MmtsTrainController mmtsTrainController;

	@Autowired
	GoogleHomeServices googleHomeServices;

	@RequestMapping(value = { "/callback/google/findTrain" }, method = { RequestMethod.POST })
	public GoogleResponse getChat(@RequestBody JsonNode requestBody) {
		System.out.println("request is - " + requestBody.toString());
		if (requestBody.has("result") && requestBody.get("result").has("metadata")
				&& requestBody.get("result").get("metadata").has("intentName")
				&& requestBody.get("result").get("parameters").has("userInput")) {
			Chat output = mmtsTrainController.converse("123",
					new Chat(requestBody.get("result").get("parameters").get("userInput").asText()));
			return googleHomeServices.constructGoogleResponse(output.getMessage(), output.isTheEnd());
		}
		return googleHomeServices.constructGoogleResponse("NEEDS DEVELOPMENT", false);
	}

}