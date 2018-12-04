package com.vadrin.mmtstrain.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.alexa.AlexaCardAndSpeech;
import com.vadrin.mmtstrain.models.alexa.AlexaResponse;
import com.vadrin.mmtstrain.utils.Util;

@Service
public class AlexaService {

	public AlexaResponse constructAlexaResponse(String response, boolean endSession) {
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
	
	public AlexaResponse autoFetchSlots() {
		List<Map<String, Object>> directives = new ArrayList<Map<String, Object>>();
		Map<String, Object> autofetch = new HashMap<String, Object>();
		autofetch.put("type", "Dialog.Delegate");
		directives.add(autofetch);
		AlexaResponse toReturn = new AlexaResponse("1.0", new HashMap<String, Object>(),
				new AlexaCardAndSpeech(null, null, false, directives));
		System.out.println("respose is - " + Util.getJson(toReturn).toString());
		return toReturn;
	}

	public AlexaResponse constructAlexaResponse(Chat chat) {
		return constructAlexaResponse(chat.getMessage(), chat.isTheEnd());
	}

}
