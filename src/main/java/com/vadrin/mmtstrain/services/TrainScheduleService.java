package com.vadrin.mmtstrain.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vadrin.mmtstrain.models.InvalidStationNamesException;
import com.vadrin.mmtstrain.models.Train;

@Service
public class TrainScheduleService {
	
	@Autowired
	StationNameToIDService stationNameToIDService;

	private static final String baseURL = "https://www.mmtstrains.com/MMTSTimingService.svc/trainsbetween/";
	
	@Autowired
	private RestTemplate restTemplate;

  public List<Train> getSchedule(String from, String to, String time) throws InvalidStationNamesException {
    String fromId = stationNameToIDService.getID(from);
    String toId = stationNameToIDService.getID(to);
    if (fromId == null || toId == null) {
      throw new InvalidStationNamesException();
    }
    try {
      Train[] allTrains = getSchedule(fromId, toId, increaseHours(time, -15), increaseHours(time, 120));
      return Arrays.asList(allTrains).stream().filter(x -> {
        Instant now = Instant.now();
        ZonedDateTime india = now.atZone(ZoneId.of("Asia/Kolkata"));
        if (DayOfWeek.SUNDAY == DayOfWeek.of(india.get(ChronoField.DAY_OF_WEEK))) {
          return x.getRunsonweekend().equalsIgnoreCase("y");
        } else {
          return true;
        }
      }).collect(Collectors.toList());
    } catch (ParseException e) {
      return Arrays.asList(getSchedule(fromId, toId, "01:00", "23:00"));
    }
  }
	
  private Train[] getSchedule(String fromId, String toId, String startTime, String endTime) {
    String url = baseURL + fromId + "/" + toId + "/" + startTime + "/" + endTime;
    return restTemplate.getForObject(url, Train[].class);
  }
	
	private String increaseHours(String input, int min) throws ParseException{
		input = input.contains(":") ? input : input+":00";
		DateFormat formatterIn = new SimpleDateFormat("HH:mm");
		Date inputTime = formatterIn.parse(input);
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputTime);
		cal.add(Calendar.MINUTE, min);
		DateFormat formatterOut = new SimpleDateFormat("HHmm");
		return formatterOut.format(cal.getTime());
	}

}
