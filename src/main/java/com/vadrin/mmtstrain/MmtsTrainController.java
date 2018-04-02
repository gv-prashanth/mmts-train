package com.vadrin.mmtstrain;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.alexa.dto.AlexaResponse;
import com.vadrin.mmtstrain.alexa.services.AlexaServices;
import com.vadrin.mmtstrain.google.dto.GoogleResponse;
import com.vadrin.mmtstrain.google.services.GoogleServices;
import com.vadrin.mmtstrain.mmtsservices.TrainScheduleService;
import com.vadrin.mmtstrain.utils.Util;

@RestController
public class MmtsTrainController {

	private static final String WELCOME_RESPONSE = "Welcome to MMTS Train Enquiry. What are your source and destination stations?";
	private static final String DIDNT_UNDERSTAND_RESPONSE = "I didnt quite understand you. Please try asking me differently.";
	private static final String BYEBYE_RESPONSE = "Bye Bye! Thank you for using MMTS Train Service.";

	@Autowired
	TrainScheduleService trainScheduleService;
	@Autowired
	AlexaServices alexaServices;
	@Autowired
	GoogleServices googleServices;

	@RequestMapping(value = { "/alexa" }, method = { RequestMethod.POST })
	public AlexaResponse getChat(@RequestBody JsonNode requestBody) throws ParseException {
		System.out.println("request is - " + requestBody.toString());
		String requestType = requestBody.get("request").get("type").asText();
		switch (requestType) {
		case "LaunchRequest":
			return alexaServices.constructAlexaResponse(WELCOME_RESPONSE, false);
		case "SessionEndedRequest":
			return alexaServices.constructAlexaResponse(BYEBYE_RESPONSE, true);
		case "IntentRequest":
			String intentName = requestBody.get("request").get("intent").get("name").asText();
			switch (intentName) {
			case "findMMTS":
				JsonNode slots = requestBody.get("request").get("intent").get("slots");
				if (slots.get("from").has("value") && slots.get("to").has("value") && slots.get("time").has("value")) {
					String from = slots.get("from").get("value").asText();
					String to = slots.get("to").get("value").asText();
					String time = slots.get("time").get("value").asText();
					String responseString = trainScheduleService.getResponseString(from, to, time);
					return alexaServices.constructAlexaResponse(responseString, true);
				} else {
					return alexaServices.constructAlexaResponse(DIDNT_UNDERSTAND_RESPONSE, false);
				}
			default:
				return alexaServices.constructAlexaResponse(DIDNT_UNDERSTAND_RESPONSE, false);
			}
		default:
			return alexaServices.constructAlexaResponse(DIDNT_UNDERSTAND_RESPONSE, false);
		}
	}

	@RequestMapping(value = { "/google" }, method = { RequestMethod.POST })
	public JsonNode getGoogleChat(@RequestBody JsonNode requestBody) throws ParseException, JsonParseException, JsonMappingException, IOException {
		System.out.println("request is - " + requestBody.toString());
		return Util.getJsonFromString("{ \"speech\": \"this text is spoken out loud if the platform supports voice interactions\", \"displayText\": \"this text is displayed visually\", \"source\": \"example.com\"}");
		/*
		if (requestBody.has("result") && requestBody.get("result").has("metadata")  && requestBody.get("result").get("metadata").has("intentName")) {
			String intentName = requestBody.get("result").get("metadata").get("intentName").asText();
			switch (intentName) {
			case "findMMTS":
				JsonNode slots = requestBody.get("result").get("parameters");
				if (slots.has("from") && slots.has("to")) {
					String from = slots.get("from").asText();
					String to = slots.get("to").asText();
					String responseString = trainScheduleService.getResponseString(from, to);
					return googleServices.constructGoogleResponse(responseString, true);
				} else {
					return googleServices.constructGoogleResponse(DIDNT_UNDERSTAND_RESPONSE, false);
				}
			default:
				return googleServices.constructGoogleResponse(DIDNT_UNDERSTAND_RESPONSE, false);
			}
		} else {
			return googleServices.constructGoogleResponse(DIDNT_UNDERSTAND_RESPONSE, false);
		}
		*/
	}
}