package com.capstone.core.util.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static String getCurrentDateTimeString(LocalDateTime currentLocalDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuuMMddHHmmss");
        return currentLocalDateTime.format(dateTimeFormatter);
    }
}
