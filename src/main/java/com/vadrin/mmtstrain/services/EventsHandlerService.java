package com.vadrin.mmtstrain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vadrin.mmtstrain.exceptions.InvalidStationNamesException;
import com.vadrin.mmtstrain.models.Chat;
import com.vadrin.mmtstrain.models.Event;
import com.vadrin.mmtstrain.models.Train;

@Service
public class EventsHandlerService {

	@Autowired
	TrainScheduleService trainScheduleService;

	private static final String TROUBLE_UNDERSTANDING = "I am having trouble understanding you. Please try later.";
	private static final String TROUBLE_UNDERSTANDING_STATION = "I am having trouble understanding the stations you have mentioned. Please try again.";
	private static final String GREET = "Hello! I can help you find a MMTS train. Just ask me for a MMTS from source station to destination station.";
	private static final String BYE = "Bye Bye! Happy Journey!";

	public Chat handle(String conversationId, Event event) {
		try{
			switch (event.getName()) {
			case "LaunchRequest":
				return new Chat(GREET, false);
			case "Default Welcome Intent":
				return new Chat(GREET, false);
			case "AMAZON.HelpIntent":
				return new Chat(GREET, false);
			case "findTrain":
				return new Chat(formatScheduleInEnglish(trainScheduleService.getSchedule(event.getInfo().get("from"),
						event.getInfo().get("to"), event.getInfo().get("time"))), true);
			case "AMAZON.CancelIntent":
				return new Chat(BYE, true);
			case "AMAZON.StopIntent":
				return new Chat(BYE, true);
			case "SessionEndedRequest":
				return new Chat(BYE, true);
			default:
				return new Chat(TROUBLE_UNDERSTANDING, true);
			}
		}catch(InvalidStationNamesException ex){
			return new Chat(TROUBLE_UNDERSTANDING_STATION, true);
		}
	}

	private String formatScheduleInEnglish(Train[] allSchedules) {
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