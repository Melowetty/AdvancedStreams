package com.melowetty.advancedstreams.apis;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.utils.Helper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class YouTubeAPI extends AbstractAPI {
    public YouTubeAPI(String id) {
        this.ApiKey = AdvancedStreams.getInstance().getSettingsManager().getYouTubeKey();
        this.id = id;
    }
    @Override
    public int getViewers() {
        try {
            String url = "https://youtube.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=" + id + "&key=" + ApiKey;
            JSONObject json = (JSONObject) JSONValue.parse(Helper.arrayListToString(Helper.getContent(url)));
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jstats = (JSONObject)j1.get("liveStreamingDetails");
            return Integer.parseInt((String) jstats.get("concurrentViewers"));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Long getDuration() {
        try {
            String url = "https://youtube.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=" + id + "&key=" + ApiKey;
            JSONObject json = (JSONObject) JSONValue.parse(Helper.arrayListToString(Helper.getContent(url)));
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jstats = (JSONObject)j1.get("liveStreamingDetails");
            String startTime = (String)jstats.get("actualStartTime");
            return Helper.youtubeDataToLong(startTime);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getTitle() {
        try {
            String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + id + "&key=" + ApiKey;
            JSONObject json = (JSONObject)JSONValue.parse(Helper.arrayListToString(Helper.getContent(url)));
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jstats = (JSONObject)j1.get("snippet");
            return jstats.get("title").toString();
        } catch (Exception e) {
            return null;
        }
    }
}
