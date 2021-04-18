package com.melowetty.advancedstreams.utils;

import com.melowetty.advancedstreams.enums.StreamPlatform;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateHelper {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public static String formatDuration(Long start, StreamPlatform platform) {
        if (platform == StreamPlatform.VK) {
            Long duration = getVKServerTime() - start;
            return getHoursAndMinutesFromDuration(duration);
        } else {
            ZonedDateTime now = ZonedDateTime.now();
            Date dateAtGmt0 = toGmt0(now);
            long duration = dateAtGmt0.getTime() - start;
            return getHoursAndMinutesFromDuration(duration/1000);
        }
    }
    public static Long getVKServerTime() {
        try {
            String url = "https://api.vk.com/method/utils.getServerTime?access_token=c98b21a5c98b21a5c98b21a5dfc9fef8cbcc98bc98b21a59672c15b56abbd7f09950db5&v=5.21";
            JSONObject json = Helper.parseURL(url);
            return Helper.objectToLong(json.get("response"));
        } catch (NullPointerException e) {
            Helper.debug(e);
            return 0L;
        }
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

        if(hr == 0) return minStr + " минут";
        else return hrStr + " часов " + minStr + " минут";
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
