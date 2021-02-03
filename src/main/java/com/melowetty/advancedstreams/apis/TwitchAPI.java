package com.melowetty.advancedstreams.apis;

import com.melowetty.advancedstreams.AdvancedStreams;

public class TwitchAPI extends AbstractAPI {
    public TwitchAPI(String id) {
        this.ApiKey = AdvancedStreams.getInstance().getSettingsManager().getTwitchKey();
        this.id = id;
    }

    @Override
    public int getViewers() {
        return 0;
    }

    @Override
    public Long getDuration() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }
}