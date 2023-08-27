package com.vadrin.mmtstrain.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.alexa.AlexaResponse;
import com.vadrin.mmtstrain.services.AlexaService;
import com.vadrin.mmtstrain.services.JsonService;

@RestController
public class AlexaCallbackController {

	@Autowired
	AlexaService alexaService;

	@PostMapping("/callback/alexa")
	public ResponseEntity<AlexaResponse> callback(HttpServletRequest request) {
		try {
	    JsonNode alexaRequestBody = JsonService.getJson(request.getInputStream().readAllBytes());
	    System.out.println("request is - " + alexaRequestBody.toString());
	    return new ResponseEntity<>(alexaService.respond(alexaRequestBody), HttpStatus.OK);
		} catch (IOException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
	}

}