package com.vadrin.mmtstrain;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.alexa.dto.AlexaResponse;
import com.vadrin.mmtstrain.alexa.services.AlexaServices;
import com.vadrin.mmtstrain.services.TrainScheduleService;

@RestController
public class MmtsTrainController {

	private static final String WELCOME_RESPONSE = "Welcome to MMTS Train Enquiry. What are your source and destination stations?";
	private static final String DIDNT_UNDERSTAND_RESPONSE = "I didnt quite understand you. Please try asking me differently.";
	private static final String BYEBYE_RESPONSE = "Bye Bye! Thank you for using MMTS Train Service.";

	@Autowired
	TrainScheduleService trainScheduleService;
	@Autowired
	AlexaServices alexaServices;

	@RequestMapping(value = { "/alexa" }, method = { RequestMethod.POST })
	public AlexaResponse getChat(@RequestBody JsonNode requestBody) throws ParseException {
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
				String from = slots.get("from").get("value").asText();
				String to = slots.get("to").get("value").asText();
				String time = slots.get("time").get("value").asText();
				String responseString = trainScheduleService.getResponseString(from, to, time);
				return alexaServices.constructAlexaResponse(responseString, true);
			default:
				return alexaServices.constructAlexaResponse(DIDNT_UNDERSTAND_RESPONSE, false);
			}
		default:
			return alexaServices.constructAlexaResponse(DIDNT_UNDERSTAND_RESPONSE, false);
		}
	}
}