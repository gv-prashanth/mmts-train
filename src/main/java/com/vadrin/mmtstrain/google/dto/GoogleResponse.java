package com.vadrin.mmtstrain.google.dto;

public class GoogleResponse {

	private String speech;
	private String displayText;
	private Messages messages;

	public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

}
