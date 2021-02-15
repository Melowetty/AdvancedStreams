package com.melowetty.advancedstreams.apis;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.utils.DateHelper;
import com.melowetty.advancedstreams.utils.Helper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class YouTubeAPI extends AbstractAPI {
    public YouTubeAPI(String id) {
        this.ApiKey = AdvancedStreams.getInstance().getSettingsManager().getYouTubeKey();
        this.id = id;
    }
    @Override
    public int getViewers() {
        try {
            String url = "https://youtube.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=" + id + "&key=" + ApiKey;
            JSONObject json = Helper.parseURL(url);
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jStats = (JSONObject)j1.get("liveStreamingDetails");
            return Helper.objectToInt( jStats.get("concurrentViewers") );
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Long getDuration() {
        try {
            String url = "https://youtube.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=" + id + "&key=" + ApiKey;
            JSONObject json = Helper.parseURL(url);
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jStats = (JSONObject)j1.get("liveStreamingDetails");
            String startTime = (String) jStats.get("actualStartTime");
            return DateHelper.stringDataToLong(startTime);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getTitle() {
        try {
            String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + id + "&key=" + ApiKey;
            JSONObject json = Helper.parseURL(url);
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jStats = (JSONObject)j1.get("snippet");
            return Helper.objectToString( jStats.get("title") );
        } catch (Exception e) {
            return null;
        }
    }
}
