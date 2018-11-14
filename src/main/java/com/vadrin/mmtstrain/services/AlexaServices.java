package com.vadrin.mmtstrain.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vadrin.mmtstrain.models.alexa.AlexaCardAndSpeech;
import com.vadrin.mmtstrain.models.alexa.AlexaResponse;
import com.vadrin.mmtstrain.utils.Util;

@Service
public class AlexaServices {

	public AlexaResponse constructAlexaResponse(String response, boolean endSession) {
		Map<String, Object> speech = new HashMap<String, Object>();
		speech.put("type", "PlainText");
		speech.put("text", response);

		Map<String, Object> card = new HashMap<String, Object>();
		card.put("type", "Standard");
		card.put("title", "MMTS Train");
		card.put("text", response);

		Map<String, Object> image = new HashMap<String, Object>();
		image.put("smallImageUrl",
				"https://lh3.ggpht.com/i8pFz2qe9qbp9NXwOr4HCd_0vnHMx0I98wwD_La6kI_42MDQgOAN4osZXnFzOoF4FQ=w200");
		image.put("largeImageUrl",
				"https://lh4.ggpht.com/8X59wxLd8TakKdaOHTmf0teySL2-S7VoMlxk6DyUiq27nCqzVt5T4DLrlCaC7oSZkn4");
		card.put("image", image);
		AlexaResponse toReturn = new AlexaResponse("1.0", new HashMap<String, Object>(),
				new AlexaCardAndSpeech(speech, card, endSession));
		System.out.println("respose is - " + Util.getJson(toReturn).toString());
		return toReturn;
	}

}