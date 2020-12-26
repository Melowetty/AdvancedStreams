package com.melowetty.advancedstreams.Utils;

import java.text.SimpleDateFormat;

public class Helper {
    public static String formatDurationToString(Long duration) {
        Long real_duration = Math.abs(duration - System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("HHч. mmмин.");
        return format.format(real_duration);
    }
    public static String stripColors(String msg) {
        String out = msg.replaceAll("[&][0-9a-fk-or]", "");
        out = out.replaceAll(String.valueOf((char) 194), "");
        return out.replaceAll("[\u00a7][0-9a-fk-or]", "");
    }
}
