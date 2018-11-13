package com.vadrin.mmtstrain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vadrin.mmtstrain.models.Train;
import com.vadrin.mmtstrain.services.DialogflowServices;
import com.vadrin.mmtstrain.services.TrainScheduleService;
import com.vadrin.mmtstrain.utils.Util;

@RestController
public class MmtsTrainController {

	@Autowired
	TrainScheduleService trainScheduleService;
	@Autowired
	DialogflowServices googleServices;

	@RequestMapping(value = { "/train" }, method = { RequestMethod.GET })
	public Train[] getTrain(@RequestParam("requestQuery") String requestQuery) {
		System.out.println(requestQuery);
		String from = "arts college";
		String to = "hitech city";
		String time = "17:00";
		Train[] toReturn;
		try{
			toReturn = trainScheduleService.getSchedule(from, to, time);
			System.out.println(Util.formatScheduleInEnglish(toReturn));
		}catch(Exception e){
			e.printStackTrace();
			toReturn = new Train[0];
		}
		return toReturn;
	}

}