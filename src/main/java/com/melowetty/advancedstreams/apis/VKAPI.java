package com.melowetty.advancedstreams.apis;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.utils.Helper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class VKAPI extends AbstractAPI {
    public VKAPI(String ownerId, String id) {
        this.ApiKey = AdvancedStreams.getInstance().getSettingsManager().getVKKey();
        this.ownerId = ownerId;
        this.id = id;
    }


    @Override
    public int getViewers() {
        try {
            String url = String.format("https://api.vk.com/method/video.get?owner_Id=%s&videos=%s_%s&access_token=%s&v=5.126", ownerId, ownerId, id, ApiKey);
            JSONObject json = (JSONObject) JSONValue.parse(Helper.arrayListToString(Helper.getContent(url)));
            JSONObject response = (JSONObject) json.get("response");
            JSONArray items = (JSONArray)response.get("items");
            JSONObject jTemp = (JSONObject) items.get(0);
            return Integer.parseInt( String.valueOf( jTemp.get("spectators") ) );
        } catch (Exception e) {
            Helper.debug(e);
            return 0;
        }
    }

    @Override
    public Long getDuration() {
        try {
            String url = String.format("https://api.vk.com/method/video.get?owner_Id=%s&videos=%s_%s&access_token=%s&v=5.126", ownerId, ownerId, id, ApiKey);
            JSONObject json = (JSONObject) JSONValue.parse(Helper.arrayListToString(Helper.getContent(url)));
            JSONObject response = (JSONObject) json.get("response");
            JSONArray items = (JSONArray)response.get("items");
            JSONObject jTemp = (JSONObject) items.get(0);
            return Long.parseLong( String.valueOf( jTemp.get("date") ) );
        } catch (Exception e) {
            Helper.debug(e);
            return null;
        }
    }

    @Override
    public String getTitle() {
        try {
            String url = String.format("https://api.vk.com/method/video.get?owner_Id=%s&videos=%s_%s&access_token=%s&v=5.126", ownerId, ownerId, id, ApiKey);
            JSONObject json = (JSONObject) JSONValue.parse(Helper.arrayListToString(Helper.getContent(url)));
            JSONObject response = (JSONObject) json.get("response");
            JSONArray items = (JSONArray)response.get("items");
            JSONObject jTemp = (JSONObject) items.get(0);
            return ((String) jTemp.get("title"));
        } catch (Exception e) {
            return null;
        }
    }
}
