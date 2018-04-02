package com.vadrin.mmtstrain.google.services;

import org.springframework.stereotype.Component;

import com.vadrin.mmtstrain.google.dto.GoogleResponse;
import com.vadrin.mmtstrain.google.dto.Messages;

@Component
public class GoogleServices {

	public GoogleResponse constructGoogleResponse(String response, boolean endSession) {
		GoogleResponse googleResponse = new GoogleResponse();
		Messages messages = new Messages();
		messages.setType(1);
		messages.setTitle("MMTS Train");
		messages.setSubtitle("MMTS Train");
		messages.setImageUrl(
				"https://lh3.ggpht.com/i8pFz2qe9qbp9NXwOr4HCd_0vnHMx0I98wwD_La6kI_42MDQgOAN4osZXnFzOoF4FQ=w200");
		googleResponse.setMessages(messages);
		googleResponse.setDisplayText(response);
		googleResponse.setSpeech(response);
		return googleResponse;
	}

}
