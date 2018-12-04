package com.vadrin.mmtstrain.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.Event;
import com.vadrin.mmtstrain.models.googlehome.GoogleResponse;
import com.vadrin.mmtstrain.services.DialogflowService;
import com.vadrin.mmtstrain.utils.Util;

@RestController
public class GoogleHomeCallbackController {

	@Autowired
	MmtsTrainController mmtsTrainController;

	@Autowired
	DialogflowService dialogflowService;

	@RequestMapping(value = { "/callback/google" }, method = { RequestMethod.POST })
	public GoogleResponse getChat(@RequestBody JsonNode requestBody) {
		System.out.println("request is - " + requestBody.toString());
		Map<String, String> params = Util.getMapFromJson(requestBody.get("result").get("parameters"));
		Event input = new Event(requestBody.get("result").get("metadata").get("intentName").asText(), params);
		Chat output = mmtsTrainController.converse(requestBody.get("sessionId").asText(), input);
		return dialogflowService.constructGoogleResponse(output);
	}

}