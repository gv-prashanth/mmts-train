package com.vadrin.mmtstrain.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vadrin.mmtstrain.models.Response;
import com.vadrin.mmtstrain.models.Intent;
import com.vadrin.mmtstrain.models.InvalidStationNamesException;
import com.vadrin.mmtstrain.models.IntentName;
import com.vadrin.mmtstrain.services.ChatService;

@RestController
public class WebController {
	
	@Autowired
	ChatService chatService;

	@GetMapping("/trains")
	public String callback(@RequestParam String from, @RequestParam String to, @RequestParam String time) throws InvalidStationNamesException {
	  Map<String, String> info = new HashMap<>();
	  info.put("from", from);
	  info.put("to", to);
	  info.put("time", time);
	  Response response = chatService.handleIntentRequest("web", new Intent(IntentName.FINDTRAIN, info));
	  return response.getMessage();
	}

}