package com.capstone.core.util.time;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeUtils {
    public static String getCurrentDateTimeString(LocalDateTime currentLocalDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuuMMddHHmmss");
        return currentLocalDateTime.format(dateTimeFormatter);
    }

    public static Long calculateIntervals(LocalTime startTime, LocalTime endTime, int intervalMinutes) {
        Long totalMinutes = ChronoUnit.MINUTES.between(startTime, endTime);
        return totalMinutes / intervalMinutes;
    }
}
