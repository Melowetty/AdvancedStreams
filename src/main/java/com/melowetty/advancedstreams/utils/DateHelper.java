package com.melowetty.advancedstreams.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateHelper {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public static String formatDuration(Long start) {

        ZonedDateTime now = ZonedDateTime.now();
        Date dateAtGmt0 = toGmt0(now);
        Long duration = dateAtGmt0.getTime() - start;
        return getHoursAndMinutesFromDuration(duration/1000);
    }
    public static String getHoursAndMinutesFromDuration(Long duration) {
        int time = duration.intValue();
        int hr = 0;
        int min = 0;
        String minStr = "";
        String hrStr = "";

        hr = (int) Math.floor((time / 60) / 60);
        min = ((int) Math.floor((time / 60)) - (hr * 60));

        hrStr = (hr < 10) ? String.valueOf(hr) : String.valueOf(hr);
        minStr = (min < 10) ? String.valueOf(min) : String.valueOf(min);

        return hrStr + " часов " + minStr + " минут";
    }
    protected static Date toGmt0(ZonedDateTime time) {
        ZonedDateTime gmt0 = time.minusSeconds(time.getOffset().getTotalSeconds());
        return Date.from(gmt0.toInstant());
    }
    public static Long stringDataToLong(String nonFormatDuration) {
        try {
            return format.parse(nonFormatDuration).getTime();
        } catch (ParseException e) {
            Helper.debug(e);
        }
        return null;
    }
    public static String dataToString(Long duration) {
        return format.format(duration);
    }
}
