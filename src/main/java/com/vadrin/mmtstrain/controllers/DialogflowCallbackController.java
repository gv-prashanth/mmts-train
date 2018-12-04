package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.mmtstrain.models.dialogflow.DialogflowResponse;
import com.vadrin.mmtstrain.services.DialogflowService;

@RestController
public class DialogflowCallbackController {

	@Autowired
	DialogflowService dialogflowService;

	@RequestMapping(value = { "/callback/google" }, method = { RequestMethod.POST })
	public DialogflowResponse callback(@RequestBody JsonNode dialogflowRequestBody) {
		System.out.println("request is - " + dialogflowRequestBody.toString());
		return dialogflowService.respond(dialogflowRequestBody);
	}

}