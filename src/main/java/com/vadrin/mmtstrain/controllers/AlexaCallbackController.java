package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<AlexaResponse> callback(@RequestBody JsonNode alexaRequestBody) {
    // JsonNode alexaRequestBody = JsonService.getJson(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
    System.out.println("request is - " + alexaRequestBody.toString());
    return new ResponseEntity<>(alexaService.respond(alexaRequestBody), HttpStatus.OK);
  }

}