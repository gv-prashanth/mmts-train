package com.vadrin.mmtstrain.alexa.dto;

import java.util.Map;

public class AlexaResponse {

	String version;
	Map<String, Object> sessionAttributes;
	AlexaCardAndSpeech response;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, Object> getSessionAttributes() {
		return sessionAttributes;
	}

	public void setSessionAttributes(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}

	public AlexaCardAndSpeech getResponse() {
		return response;
	}

	public void setResponse(AlexaCardAndSpeech response) {
		this.response = response;
	}

	public AlexaResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AlexaResponse(String version, Map<String, Object> sessionAttributes, AlexaCardAndSpeech response) {
		super();
		this.version = version;
		this.sessionAttributes = sessionAttributes;
		this.response = response;
	}

}
