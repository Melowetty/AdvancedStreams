package com.melowetty.advancedstreams.APIs;

import com.melowetty.advancedstreams.AdvancedStreams;

public class TwitchAPI extends AbstractAPI {
    public TwitchAPI(String id) {
        this.API_key = AdvancedStreams.getInstance().getSettingsManager().getTwitchKey();
        this.id = id;
    }
    @Override
    public Long getDuration() {
        return null;
    }

    @Override
    public int getViewers() {
        return 0;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
