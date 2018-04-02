package com.vadrin.mmtstrain.google.dto;

import java.util.List;

public class GoogleResponse {

	private String speech;
	private String displayText;
	private Messages messages;
	private Object data;
	private List<Object> contextOut;
	private String source;
	private Object followupEvent;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<Object> getContextOut() {
		return contextOut;
	}

	public void setContextOut(List<Object> contextOut) {
		this.contextOut = contextOut;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Object getFollowupEvent() {
		return followupEvent;
	}

	public void setFollowupEvent(Object followupEvent) {
		this.followupEvent = followupEvent;
	}

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
