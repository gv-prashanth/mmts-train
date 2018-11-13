package com.vadrin.mmtstrain.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadrin.mmtstrain.models.Train;

public class Util {

	private static ObjectMapper om = new ObjectMapper();

	public static <T> JsonNode getJsonFromMap(Map<String, T> jsonMap) throws IllegalArgumentException {
		return om.convertValue(jsonMap, JsonNode.class);
	}

	public static JsonNode getJsonFromString(String jsonString)
			throws IOException, JsonParseException, JsonMappingException {
		return om.readValue(jsonString, JsonNode.class);
	}

	public static JsonNode getJson(Object object) {
		return om.convertValue(object, JsonNode.class);
	}

	public static <T> Object getObjectFromJson(JsonNode jsonNode, Class<T> valueType) throws JsonProcessingException {
		return om.treeToValue(jsonNode, valueType);
	}

	public static <T> Map<String, T> getMapFromJson(JsonNode jsonNode) throws IllegalArgumentException {
		return om.convertValue(jsonNode, Map.class);
	}
	
	public static String increaseHours(String input, int hours) throws ParseException{
		DateFormat formatterIn = new SimpleDateFormat("HH:mm");
		Date inputTime = formatterIn.parse(input);
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputTime);
		cal.add(Calendar.HOUR, hours);
		DateFormat formatterOut = new SimpleDateFormat("HHmm");
		return formatterOut.format(cal.getTime());
	}
	
	public static String formatScheduleInEnglish(Train[] allSchedules) {
		if (allSchedules.length > 1) {
			return "Ok. I found a mmts from " + allSchedules[0].getStartstation() + " to "
					+ allSchedules[0].getStopstation() + " at " + allSchedules[0].getStarttime()
					+ ". The next one is at " + allSchedules[1].getStarttime() + ".";
		} else if (allSchedules.length > 0) {
			return "Ok. I found a mmts from " + allSchedules[0].getStartstation() + " to "
					+ allSchedules[0].getStopstation() + " at " + allSchedules[0].getStarttime() + ".";
		} else {
			return "Unfortunately. I dont see any trains around the time you have mentioned.";
		}
	}

}
