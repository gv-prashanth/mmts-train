package com.vadrin.mmtstrain.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.services.Serializer;
import com.amazon.ask.servlet.ServletConstants;
import com.amazon.ask.servlet.verifiers.ServletRequest;
import com.amazon.ask.servlet.verifiers.SkillRequestSignatureVerifier;
import com.amazon.ask.servlet.verifiers.SkillRequestTimestampVerifier;
import com.amazon.ask.servlet.verifiers.SkillServletVerifier;
import com.amazon.ask.util.JacksonSerializer;
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
      byte[] serializedRequestEnvelope = IOUtils.toByteArray(request.getInputStream());
      Serializer serializer = new JacksonSerializer();
      final RequestEnvelope deserializedRequestEnvelope = serializer.deserialize(IOUtils.toString(
          serializedRequestEnvelope, ServletConstants.CHARACTER_ENCODING), RequestEnvelope.class);
		  final ServletRequest alexaRequest = new ServletRequest(request, serializedRequestEnvelope, deserializedRequestEnvelope);
		  SkillServletVerifier sigVerified = new SkillRequestSignatureVerifier();
		  SkillServletVerifier timeVerified = new SkillRequestTimestampVerifier(150000L);
		  sigVerified.verify(alexaRequest);
		  timeVerified.verify(alexaRequest);
	    return new ResponseEntity<>(alexaService.respond(alexaRequestBody), HttpStatus.OK);
		}catch(SecurityException e) {
		  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
	}

}