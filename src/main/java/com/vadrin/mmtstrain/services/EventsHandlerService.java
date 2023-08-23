package com.vadrin.mmtstrain.services;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vadrin.mmtstrain.models.Train;

@Service
public class EventsHandlerService {

	public String formatScheduleInEnglish(Train[] allSchedules) {
		if (allSchedules.length > 1) {
			return "Ok. I found " + allSchedules.length + " mmts from " + allSchedules[0].getStartstation() + " to "
          + allSchedules[0].getStopstation()+". They are at " + Arrays.stream(allSchedules).map(Train::getStarttime).collect(Collectors.joining(", ")) + ".";
		} else if (allSchedules.length > 0) {
			return "Ok. I found a mmts from " + allSchedules[0].getStartstation() + " to "
					+ allSchedules[0].getStopstation() + " at " + allSchedules[0].getStarttime() + ".";
		} else {
			return "Unfortunately. I dont see any trains around the time you have mentioned.";
		}
	}

}