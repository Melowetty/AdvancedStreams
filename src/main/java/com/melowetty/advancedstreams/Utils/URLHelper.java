package com.melowetty.advancedstreams.Utils;

import com.melowetty.advancedstreams.APIs.YouTubeAPI;
import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.StreamPlatform;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class URLHelper {
    public static ArrayList<String> getContent(String surl) {
        ArrayList<String> content = new ArrayList<>();
        try {
            URL url = new URL(surl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF8"));
            String Line;
            while ((Line = in.readLine()) != null)
                content.add(Line);
            in.close();
            return content;
        } catch (Exception e) {
            Helper.debug(e);
            return content;
        }
    }

    public static String arrayListToString(ArrayList<String> list) {
        StringBuilder content = new StringBuilder();
        for (String s : list)
            content.append(s);
        return content.toString();
    }
    public static String getYouTubeVideoID(String url) {
        if(!url.contains("youtu")) return null;
        if(url.contains("youtu.be")) {
            return url.substring(url.lastIndexOf('/')+1);
        }
        else {
            return url.substring(url.lastIndexOf("?v")+3);
        }
    }
    public static String getTitle(StreamPlatform platform, String id) {
        switch (platform) {
            case YOUTUBE:
                YouTubeAPI youTubeAPI = new YouTubeAPI(id);
                return youTubeAPI.getTitle();
            default:
                return null;
        }
    }
}
