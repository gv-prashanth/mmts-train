package com.vadrin.mmtstrain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vadrin.mmtstrain.models.Intent;
import com.vadrin.mmtstrain.models.InvalidStationNamesException;
import com.vadrin.mmtstrain.models.Response;
import com.vadrin.mmtstrain.models.Train;

@Service
public class ChatService {
  
  private static final String GREET = "Hello! I can help you find a MMTS train. What is your source station?";
  private static final String BYE = "Bye Bye! Happy Journey!";
  private static final String HELPTEXT = "You can ask for a train from arts college to hitech city at 9AM.";
  private static final String TROUBLE_UNDERSTANDING = "I am having trouble understanding you. Please try later.";
  private static final String TROUBLE_UNDERSTANDING_STATION = "I am having trouble understanding the stations you have mentioned. Please try again.";
  
  @Autowired
  TrainScheduleService trainScheduleService;
  
  public Response handleIntentRequest(String conversationId, Intent intent) {
    System.out.println(conversationId);
    switch (intent.getName()) {
    case HELP:
      return new Response(HELPTEXT, false);
    case FINDTRAIN: {
      try {
        return new Response(formatScheduleInEnglish(trainScheduleService.getSchedule(intent.getSlots().get("from"),
            intent.getSlots().get("to"), intent.getSlots().get("time"))), true);
      } catch (InvalidStationNamesException ex) {
        return new Response(TROUBLE_UNDERSTANDING_STATION, true);
      }
    }
    case STOP:
      return new Response(BYE, true);
    case LAUNCH:
      return new Response(GREET, false);
    default:
      return new Response(TROUBLE_UNDERSTANDING, true);
    }
  }

	private String formatScheduleInEnglish(List<Train> allSchedules) {
		if (allSchedules.size() > 1) {
			return "Ok. I found " + allSchedules.size() + " mmts from " + allSchedules.get(0).getStartstation() + " to "
          + allSchedules.get(0).getStopstation()+". They are at " + allSchedules.stream().map(Train::getStarttime).collect(Collectors.joining(", ")) + ".";
		} else if (allSchedules.size() > 0) {
			return "Ok. I found a mmts from " + allSchedules.get(0).getStartstation() + " to "
					+ allSchedules.get(0).getStopstation() + " at " + allSchedules.get(0).getStarttime() + ".";
		} else {
			return "Unfortunately, I dont see any trains in next one hour of mentioned time.";
		}
	}

}