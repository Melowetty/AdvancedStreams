package com.melowetty.advancedstreams.apis;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.utils.DateHelper;
import com.melowetty.advancedstreams.utils.Helper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TwitchAPI extends AbstractAPI {
    public TwitchAPI(String id) {
        this.ApiKey = AdvancedStreams.getInstance().getSettingsManager().getTwitchAccessToken();
        this.clientId = AdvancedStreams.getInstance().getSettingsManager().getTwitchClientId();
        this.id = id;
    }

    @Override
    public int getViewers() {
        String curl = "https://api.twitch.tv/helix/streams?user_login=" + id;
        JSONObject json = Helper.parseURL(curl, ApiKey, clientId);
        JSONArray data = (JSONArray)json.get("data");
        JSONObject items = (JSONObject) data.get(0);
        return Helper.objectToInt( items.get("viewer_count") );
    }

    @Override
    public Long getDuration() {
        String curl = "https://api.twitch.tv/helix/streams?user_login=" + id;
        JSONObject json = Helper.parseURL(curl, ApiKey, clientId);
        JSONArray data = (JSONArray)json.get("data");
        JSONObject items = (JSONObject) data.get(0);
        return DateHelper.stringDataToLong( (String) items.get("started_at") );
    }

    @Override
    public String getTitle() {
        String curl = "https://api.twitch.tv/helix/streams?user_login=" + id;
        JSONObject json = Helper.parseURL(curl, ApiKey, clientId);
        JSONArray data = (JSONArray)json.get("data");
        JSONObject items = (JSONObject) data.get(0);
        return Helper.objectToString( items.get("title") );
    }
}
