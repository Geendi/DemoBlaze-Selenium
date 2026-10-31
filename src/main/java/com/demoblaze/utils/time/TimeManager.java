package com.demoblaze.utils.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TimeManager {
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss-SSS");

    private TimeManager() {
    }

    public static String getTimestamp() {
        return LocalDateTime.now().format(TIMESTAMP_FORMAT);
    }
}
