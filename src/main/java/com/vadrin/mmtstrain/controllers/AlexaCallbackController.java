package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.alexa.AlexaResponse;
import com.vadrin.mmtstrain.services.AlexaService;

@RestController
public class AlexaCallbackController {

	@Autowired
	AlexaService alexaService;

	@PostMapping("/callback/alexa")
	public AlexaResponse callback(@RequestBody JsonNode alexaRequestBody) {
		System.out.println("request is - " + alexaRequestBody.toString());
		return alexaService.respond(alexaRequestBody);
	}

}