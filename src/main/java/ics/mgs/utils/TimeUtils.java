package ics.mgs.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtils {
    private TimeUtils() {
    }

    public static final LocalDateTime convertSecond2LocalDateTime(String timeInfo) {
        long seconds = Long.parseLong(timeInfo);
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault());
    }
}
