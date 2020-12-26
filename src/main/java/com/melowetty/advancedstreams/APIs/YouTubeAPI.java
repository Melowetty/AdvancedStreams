package com.melowetty.advancedstreams.APIs;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.Utils.URLHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Date;
import java.util.HashMap;

public class YouTubeAPI {
    private final AdvancedStreams plugin = AdvancedStreams.getInstance();
    private final String API_key = plugin.getSettingsManager().getYouTubeKey();
    public int getViewers(String id) {
        HashMap<String, Long> map = new HashMap<>();
        try {
            String surl = "https://youtube.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=" + id + "&key=" + API_key;
            JSONObject json = (JSONObject) JSONValue.parse(URLHelper.arrayListToString(URLHelper.getContent(surl)));
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jstats = (JSONObject)j1.get("liveStreamingDetails");
            return Integer.parseInt((String) jstats.get("concurrentViewers"));
        } catch (Exception e) {
            return 0;
        }
    }
    public Long getDuration(String id) {
        try {
            String surl = "https://youtube.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=" + id + "&key=" + API_key;
            JSONObject json = (JSONObject) JSONValue.parse(URLHelper.arrayListToString(URLHelper.getContent(surl)));
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jstats = (JSONObject)j1.get("liveStreamingDetails");
            return new Date((String) jstats.get("actualStartTime")).getTime();
        } catch (Exception e) {
            return null;
        }
    }
    public String getTitle(String id) {
        try {
            String surl = "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + id + "&key=" + API_key;
            JSONObject json = (JSONObject)JSONValue.parse(URLHelper.arrayListToString(URLHelper.getContent(surl)));
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jstats = (JSONObject)j1.get("snippet");
            return jstats.get("title").toString();
        } catch (Exception e) {
            return null;
        }
    }
}
