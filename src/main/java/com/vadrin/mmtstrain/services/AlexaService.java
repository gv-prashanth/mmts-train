package com.vadrin.mmtstrain.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Response;
import com.vadrin.mmtstrain.models.Intent;
import com.vadrin.mmtstrain.models.IntentName;
import com.vadrin.mmtstrain.models.alexa.AlexaCardAndSpeech;
import com.vadrin.mmtstrain.models.alexa.AlexaResponse;

@Service
public class AlexaService {

	@Autowired
	ChatService chatService;
	
	public AlexaResponse respond(JsonNode alexaRequestBody) {
    String conversationId = alexaRequestBody.get("session").get("sessionId").asText();
	  String requestType = alexaRequestBody.get("request").get("type").asText();

    //Arrive at intent name using intentrequest object. if not possible arrive using request type.
    IntentName intentName = requestType.equalsIgnoreCase("IntentRequest") ? constructIntentName(alexaRequestBody.get("request").get("intent").get("name").asText()) : constructIntentName(requestType);
    Map<String, String> slots = constructIntentSlots(alexaRequestBody);
    Intent intent = new Intent(intentName, slots);
    Response response = chatService.handleIntentRequest(conversationId, intent);
    AlexaResponse toReturn =  constructAlexaResponse(response);
		if (intentName == IntentName.LAUNCH) {
		  addDirectiveToAnotherIntent(toReturn, "findTrain");
    }
    return toReturn;
	}

  private void addDirectiveToAnotherIntent(AlexaResponse toReturn, String intentToRedirect) {
    List<Map<String, Object>> directives = new ArrayList<Map<String, Object>>();
    Map<String, Object> updateIntent = new HashMap<String, Object>();
    updateIntent.put("type", "Dialog.Delegate");
    updateIntent.put("updatedIntent", intentToRedirect);
    directives.add(updateIntent);
    toReturn.getResponse().setDirectives(directives);
  }

  private IntentName constructIntentName(String name) {
    if(name.equalsIgnoreCase("findTrain")) {
      return IntentName.FINDTRAIN;
    }else if(name.equalsIgnoreCase("AMAZON.HelpIntent")) {
      return IntentName.HELP;
    }else if(name.equalsIgnoreCase("AMAZON.CancelIntent")) {
      return IntentName.STOP;
    }else if(name.equalsIgnoreCase("AMAZON.StopIntent")) {
      return IntentName.STOP;
    }else if (name.equalsIgnoreCase("LaunchRequest")) {
      return IntentName.LAUNCH;
    }else {
      return IntentName.UNKNOWN;
    }
  }

  private Map<String, String> constructIntentSlots(JsonNode alexaRequestBody) {
    Map<String, String> slots = new HashMap<String, String>();
    try {
      alexaRequestBody.get("request").get("intent").get("slots").elements().forEachRemaining(child -> {
        try {
          slots.put(child.get("name").asText(), child.get("resolutions").get("resolutionsPerAuthority")
              .get(0).get("values").get(0).get("value").get("name").asText());
        } catch (NullPointerException e) {
          slots.put(child.get("name").asText(), child.get("value").asText());
        }
      });
    } catch (NullPointerException e) {
      //No params at all
    }
    return slots;
	}

	private AlexaResponse constructAlexaResponse(Response response) {
    Map<String, Object> speech = new HashMap<String, Object>();
    speech.put("type", "PlainText");
    speech.put("text", response.getMessage());

    Map<String, Object> card = new HashMap<String, Object>();
    card.put("type", "Standard");
    card.put("title", "MMTS Train");
    card.put("text", response.getMessage());

    Map<String, Object> image = new HashMap<String, Object>();
    image.put("smallImageUrl", "https://mmts-train.vadrin.com/images/icon.png");
    image.put("largeImageUrl", "https://mmts-train.vadrin.com/images/icon.png");
    card.put("image", image);
    AlexaResponse toReturn = new AlexaResponse("1.0", new HashMap<String, Object>(),
        new AlexaCardAndSpeech(speech, card, response.isTheEnd(), null));
    System.out.println("respose is - " + JsonService.getJson(toReturn).toString());
    return toReturn;
	}

}
