package com.vadrin.mmtstrain;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.alexa.dto.AlexaCardAndSpeech;
import com.vadrin.mmtstrain.alexa.dto.AlexaResponse;

@RestController
public class MmtsTrainController {

	private static final String WELCOME_RESPONSE = "Welcome to MMTS Train Enquiry. What are your source and destination stations?";
	private static final String DIDNT_UNDERSTAND_RESPONSE = "I didnt quite understand you. Please try asking me differently.";
	private static final String BYEBYE_RESPONSE = "Bye Bye! Thank you for using MMTS Train Service.";

	@RequestMapping(value = { "/alexa" }, method = { RequestMethod.POST })
	public AlexaResponse getChat(@RequestBody JsonNode requestBody) throws IOException {
		String requestType = requestBody.get("request").get("type").asText();
		if (requestType.equalsIgnoreCase("LaunchRequest")) {
			return constructAlexaResponse(WELCOME_RESPONSE, false);
		} else if (requestType.equalsIgnoreCase("SessionEndedRequest")) {
			return constructAlexaResponse(BYEBYE_RESPONSE, true);
		} else if (requestType.equalsIgnoreCase("IntentRequest")
				&& requestBody.get("request").get("intent").get("name").asText().equalsIgnoreCase("findMMTS")) {
			JsonNode allSlots = requestBody.get("request").get("intent").get("slots");
			if (allSlots.get("time").has("value")) {
				return constructAlexaResponse(
						getMmtsTime(allSlots.get("from").get("value").asText(),
								allSlots.get("to").get("value").asText(), allSlots.get("time").get("value").asText()),
						true);
			} else {
				return constructAlexaResponse(getMmtsTime(allSlots.get("from").get("value").asText(),
						allSlots.get("to").get("value").asText()), true);
			}

		} else {
			return constructAlexaResponse(DIDNT_UNDERSTAND_RESPONSE, false);
		}
	}

	private String getMmtsTime(String from, String to, String time) {
		// TODO Auto-generated method stub
		return "Ok. I found an mmts from " + from + " to " + to + " at " + time + " today.";
	}

	private String getMmtsTime(String from, String to) {
		// TODO Auto-generated method stub
		return "Ok. The next mmts from " + from + " to " + to + " is at " + "some time" + " today.";
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
		image.put("smallImageUrl",
				"https://lh3.ggpht.com/i8pFz2qe9qbp9NXwOr4HCd_0vnHMx0I98wwD_La6kI_42MDQgOAN4osZXnFzOoF4FQ=w200");
		image.put("largeImageUrl",
				"https://lh4.ggpht.com/8X59wxLd8TakKdaOHTmf0teySL2-S7VoMlxk6DyUiq27nCqzVt5T4DLrlCaC7oSZkn4");
		card.put("image", image);

		return new AlexaResponse("1.0", new HashMap<String, Object>(), new AlexaCardAndSpeech(speech, card, true));
	}

}