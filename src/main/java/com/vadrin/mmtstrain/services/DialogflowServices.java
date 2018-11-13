package com.vadrin.mmtstrain.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vadrin.mmtstrain.models.GoogleResponse;
import com.vadrin.mmtstrain.utils.Util;

@Component
public class DialogflowServices {

	public GoogleResponse constructGoogleResponse(String response, boolean endSession) {
		GoogleResponse googleResponse = new GoogleResponse();
		//googleResponse.setMessages(messages);
		googleResponse.setDisplayText(response);
		googleResponse.setSpeech(response);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("google", getGoogleData(response, endSession));
		//googleResponse.setData(data);
		googleResponse.setSource("mmts-train.herokuapp.com");
		System.out.println("respose is - " + Util.getJson(googleResponse).toString());
		return googleResponse;
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
