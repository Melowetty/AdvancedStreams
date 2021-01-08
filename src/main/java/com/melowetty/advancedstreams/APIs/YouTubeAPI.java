package com.melowetty.advancedstreams.APIs;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.Utils.Helper;
import com.melowetty.advancedstreams.Utils.URLHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class YouTubeAPI extends AbstractAPI {
    public YouTubeAPI(String id) {
        this.API_key = AdvancedStreams.getInstance().getSettingsManager().getYouTubeKey();
        this.id = id;
    }
    @Override
    public int getViewers() {
        try {
            String url = "https://youtube.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=" + id + "&key=" + API_key;
            JSONObject json = (JSONObject) JSONValue.parse(URLHelper.arrayListToString(URLHelper.getContent(url)));
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jstats = (JSONObject)j1.get("liveStreamingDetails");
            return Integer.parseInt((String) jstats.get("concurrentViewers"));
        } catch (Exception e) {
            Helper.debug(e);
            return 0;
        }
    }

    @Override
    public Long getDuration() {
        try {
            String url = "https://youtube.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=" + id + "&key=" + API_key;
            JSONObject json = (JSONObject) JSONValue.parse(URLHelper.arrayListToString(URLHelper.getContent(url)));
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jstats = (JSONObject)j1.get("liveStreamingDetails");
            String start_time = (String)jstats.get("actualStartTime");
            return Helper.youtubeDataToLong(start_time.replace('T',' ').replace('Z', ' '));
        } catch (Exception e) {
            Helper.debug(e);
            return null;
        }
    }

    @Override
    public String getTitle() {
        try {
            String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + id + "&key=" + API_key;
            JSONObject json = (JSONObject)JSONValue.parse(URLHelper.arrayListToString(URLHelper.getContent(url)));
            JSONArray items = (JSONArray)json.get("items");
            JSONObject j1 = (JSONObject)items.get(0);
            JSONObject jstats = (JSONObject)j1.get("snippet");
            return jstats.get("title").toString();
        } catch (Exception e) {
            Helper.debug(e);
            return null;
        }
    }
}
