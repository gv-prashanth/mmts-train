package com.vadrin.mmtstrain.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.googlehome.GoogleResponse;
import com.vadrin.mmtstrain.models.googlehome.Messages;
import com.vadrin.mmtstrain.utils.Util;

@Component
public class GoogleHomeService {

	public GoogleResponse constructGoogleResponse(String response, boolean endSession) {
		GoogleResponse googleResponse = new GoogleResponse();
		Messages messages = new Messages();
		messages.setType(1);
		messages.setTitle("MMTS Train");
		messages.setSubtitle("MMTS Train");
		messages.setImageUrl("https://mmts-train-timings.herokuapp.com/images/icon.png");
		// googleResponse.setMessages(messages);
		googleResponse.setDisplayText(response);
		googleResponse.setSpeech(response);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("google", getGoogleData(response, endSession));
		// googleResponse.setData(data);
		googleResponse.setSource("mmts-train-timings.herokuapp.com");
		System.out.println("respose is - " + Util.getJson(googleResponse).toString());
		return googleResponse;
	}

	public GoogleResponse constructGoogleResponse(Chat chat) {
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
