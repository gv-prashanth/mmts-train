package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.Event;
import com.vadrin.mmtstrain.services.DialogflowResponseHandlerService;
import com.vadrin.mmtstrain.services.DialogflowService;
import com.vadrin.mmtstrain.utils.Util;

@RestController
public class MmtsTrainController {

	@Autowired
	DialogflowService dialogflowService;
	
	@Autowired
	DialogflowResponseHandlerService dialogflowResponseHandlerService;

	@RequestMapping(value = { "/conversation/id" }, method = { RequestMethod.GET })
	public String initiateConverasation() {
		return Util.generateRandomId();
	}

	@RequestMapping(value = { "/conversation/{conversationId}/chat" }, method = { RequestMethod.PUT })
	public Chat converse(@PathVariable("conversationId") String conversationId, @RequestBody Chat chat) {
		JsonNode dialogflowResponse = dialogflowService.getConverationEngineResponse(conversationId, chat);
		System.out.println(dialogflowResponse);
		return dialogflowResponseHandlerService.handle(dialogflowResponse);
	}

	@RequestMapping(value = { "/conversation/{conversationId}/event" }, method = { RequestMethod.PUT })
	public Chat converse(@PathVariable("conversationId") String conversationId, @RequestBody Event event) {
		JsonNode dialogflowResponse = dialogflowService.getConverationEngineResponse(conversationId, event);
		System.out.println(dialogflowResponse);
		return dialogflowResponseHandlerService.handle(dialogflowResponse);
	}

}