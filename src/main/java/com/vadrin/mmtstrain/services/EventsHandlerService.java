package com.vadrin.mmtstrain.services;

import org.springframework.stereotype.Service;

import com.vadrin.mmtstrain.models.Train;

@Service
public class EventsHandlerService {

	public String formatScheduleInEnglish(Train[] allSchedules) {
		if (allSchedules.length > 1) {
			return "Ok. There are " + allSchedules.length + " trains. First mmts from "
					+ allSchedules[0].getStartstation() + " to " + allSchedules[0].getStopstation() + " is at "
					+ allSchedules[0].getStarttime() + ". The next one is at " + allSchedules[1].getStarttime() + ".";
		} else if (allSchedules.length > 0) {
			return "Ok. I found a mmts from " + allSchedules[0].getStartstation() + " to "
					+ allSchedules[0].getStopstation() + " at " + allSchedules[0].getStarttime() + ".";
		} else {
			return "Unfortunately. I dont see any trains around the time you have mentioned.";
		}
	}

}