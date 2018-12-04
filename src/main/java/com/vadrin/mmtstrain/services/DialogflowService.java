package com.vadrin.mmtstrain.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.Event;
import com.vadrin.mmtstrain.models.dialogflow.DialogflowResponse;
import com.vadrin.mmtstrain.models.dialogflow.Messages;
import com.vadrin.mmtstrain.utils.Util;

@Component
public class DialogflowService {

	@Autowired
	EventsHandlerService eventsHandlerService;
	
	public DialogflowResponse respond(JsonNode dialogflowRequestBody) {
		Map<String, String> params = Util.getMapFromJson(dialogflowRequestBody.get("result").get("parameters"));
		Event input = new Event(dialogflowRequestBody.get("result").get("metadata").get("intentName").asText(), params);
		Chat output = eventsHandlerService.handle(dialogflowRequestBody.get("sessionId").asText(), input);
		return constructGoogleResponse(output);
	}

	private DialogflowResponse constructGoogleResponse(String response, boolean endSession) {
		DialogflowResponse dialogflowResponse = new DialogflowResponse();
		Messages messages = new Messages();
		messages.setType(1);
		messages.setTitle("MMTS Train");
		messages.setSubtitle("MMTS Train");
		messages.setImageUrl("https://mmts-train-timings.herokuapp.com/images/icon.png");
		// googleResponse.setMessages(messages);
		dialogflowResponse.setDisplayText(response);
		dialogflowResponse.setSpeech(response);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("google", getGoogleData(response, endSession));
		// googleResponse.setData(data);
		dialogflowResponse.setSource("mmts-train-timings.herokuapp.com");
		System.out.println("respose is - " + Util.getJson(dialogflowResponse).toString());
		return dialogflowResponse;
	}

	private DialogflowResponse constructGoogleResponse(Chat chat) {
		return constructGoogleResponse(chat.getMessage(), chat.isTheEnd());
	}

	private Object getGoogleData(String response, boolean endSession) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode toReturn = mapper.createObjectNode();
		toReturn.put("expectUserResponse", !endSession);
		ObjectNode richResponse = mapper.createObjectNode();
		ArrayNode items = mapper.createArrayNode();
		ObjectNode item = mapper.createObjectNode();
		ObjectNode simpleResponse = mapper.createObjectNode();
		simpleResponse.put("textToSpeech", response);
		item.put("simpleResponse", simpleResponse);
		items.add(item);
		richResponse.put("items", items);
		toReturn.put("richResponse", richResponse);
		return Util.getMapFromJson(toReturn);
	}

}
