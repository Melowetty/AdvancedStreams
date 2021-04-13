package com.melowetty.advancedstreams.apis;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.utils.Helper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class VKAPI extends AbstractAPI {
    private final String baseUrl = "https://api.vk.com/method/video.get?owner_Id=%s&videos=%s_%s&access_token=%s&v=5.126";
    public VKAPI(String ownerId, String id) {
        this.ApiKey = AdvancedStreams.getInstance().getSettingsManager().getVKKey();
        this.ownerId = ownerId;
        this.id = id;
    }


    @Override
    public int getViewers() {
        try {
            JSONObject jTemp = parse();
            return Helper.objectToInt( jTemp.get("spectators") );
        } catch (Exception e) {
            Helper.debug(e);
            return 0;
        }
    }

    @Override
    public Long getDuration() {
        try {
            JSONObject jTemp = parse();
            return Helper.objectToLong( jTemp.get("date") );
        } catch (Exception e) {
            Helper.debug(e);
            return null;
        }
    }

    @Override
    public String getTitle() {
        try {
            JSONObject jTemp = parse();
            return Helper.objectToString( jTemp.get("title") );
        } catch (Exception e) {
            return null;
        }
    }

    private JSONObject parse() {
        String url = String.format(baseUrl, ownerId, ownerId, id, ApiKey);
        JSONObject json = Helper.parseURL(url);
        JSONObject response = (JSONObject) json.get("response");
        JSONArray items = (JSONArray)response.get("items");
        return (JSONObject) items.get(0);
    }
}
