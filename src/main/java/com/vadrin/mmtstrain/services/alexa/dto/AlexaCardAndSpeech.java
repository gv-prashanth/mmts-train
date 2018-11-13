package com.vadrin.mmtstrain.services.alexa.dto;

import java.util.Map;

public class AlexaCardAndSpeech {

	Map<String, Object> outputSpeech;
	Map<String, Object> card;
	boolean shouldEndSession;

	public Map<String, Object> getOutputSpeech() {
		return outputSpeech;
	}

	public void setOutputSpeech(Map<String, Object> outputSpeech) {
		this.outputSpeech = outputSpeech;
	}

	public Map<String, Object> getCard() {
		return card;
	}

	public void setCard(Map<String, Object> card) {
		this.card = card;
	}

	public boolean isShouldEndSession() {
		return shouldEndSession;
	}

	public void setShouldEndSession(boolean shouldEndSession) {
		this.shouldEndSession = shouldEndSession;
	}

	public AlexaCardAndSpeech() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AlexaCardAndSpeech(Map<String, Object> outputSpeech, Map<String, Object> card, boolean shouldEndSession) {
		super();
		this.outputSpeech = outputSpeech;
		this.card = card;
		this.shouldEndSession = shouldEndSession;
	}

}
