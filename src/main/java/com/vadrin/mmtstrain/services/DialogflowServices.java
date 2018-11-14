package com.vadrin.mmtstrain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class DialogflowServices {

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	private static final String baseURL = "https://api.dialogflow.com/v1/query";
	private RestTemplate restTemplate;

	public JsonNode getConverationEngineResponse(String converastionId, String inputText) {
		restTemplate = restTemplateBuilder.build();
		String constructedURL = baseURL + "?query=" + inputText + "&lang=en&sessionId=" + converastionId;
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer 942ba94e390a450fb190f2128ffdfa48");
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<JsonNode> responseEntity = this.restTemplate.exchange(constructedURL, HttpMethod.GET, request,
				JsonNode.class);
		return responseEntity.getBody();
	}

}
