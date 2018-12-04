package com.vadrin.mmtstrain.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.Event;
import com.vadrin.mmtstrain.models.alexa.AlexaCardAndSpeech;
import com.vadrin.mmtstrain.models.alexa.AlexaResponse;
import com.vadrin.mmtstrain.utils.Util;

@Service
public class AlexaService {
	
	@Autowired
	EventsHandlerService eventsHandlerService;
	
	public AlexaResponse respond(JsonNode alexaRequestBody){
		if (alexaRequestBody.get("request").has("dialogState")
				&& !alexaRequestBody.get("request").get("dialogState").asText().equalsIgnoreCase("COMPLETED")) {
			return autoFetchSlots();
		}
		if (alexaRequestBody.get("request").get("type").asText().equalsIgnoreCase("IntentRequest")) {
			Map<String, String> eventParams = new HashMap<String, String>();
			alexaRequestBody.get("request").get("intent").get("slots").elements().forEachRemaining(
					child -> eventParams.put(child.get("name").asText(), child.get("value").asText()));
			Event input = new Event(alexaRequestBody.get("request").get("intent").get("name").asText(), eventParams);
			Chat output = eventsHandlerService.handle(alexaRequestBody.get("session").get("sessionId").asText(), input);
			return constructAlexaResponse(output);
		} else {
			Event input = new Event(alexaRequestBody.get("request").get("type").asText());
			Chat output = eventsHandlerService.handle(alexaRequestBody.get("session").get("sessionId").asText(), input);
			return constructAlexaResponse(output);
		}
	}
	
	private AlexaResponse constructAlexaResponse(String response, boolean endSession) {
		Map<String, Object> speech = new HashMap<String, Object>();
		speech.put("type", "PlainText");
		speech.put("text", response);

		Map<String, Object> card = new HashMap<String, Object>();
		card.put("type", "Standard");
		card.put("title", "MMTS Train");
		card.put("text", response);

		Map<String, Object> image = new HashMap<String, Object>();
		image.put("smallImageUrl", "https://mmts-train-timings.herokuapp.com/images/icon.png");
		image.put("largeImageUrl", "https://mmts-train-timings.herokuapp.com/images/icon.png");
		card.put("image", image);
		AlexaResponse toReturn = new AlexaResponse("1.0", new HashMap<String, Object>(),
				new AlexaCardAndSpeech(speech, card, endSession, null));
		System.out.println("respose is - " + Util.getJson(toReturn).toString());
		return toReturn;
	}
	
	private AlexaResponse autoFetchSlots() {
		List<Map<String, Object>> directives = new ArrayList<Map<String, Object>>();
		Map<String, Object> autofetch = new HashMap<String, Object>();
		autofetch.put("type", "Dialog.Delegate");
		directives.add(autofetch);
		AlexaResponse toReturn = new AlexaResponse("1.0", new HashMap<String, Object>(),
				new AlexaCardAndSpeech(null, null, false, directives));
		System.out.println("respose is - " + Util.getJson(toReturn).toString());
		return toReturn;
	}

	private AlexaResponse constructAlexaResponse(Chat chat) {
		return constructAlexaResponse(chat.getMessage(), chat.isTheEnd());
	}

}
