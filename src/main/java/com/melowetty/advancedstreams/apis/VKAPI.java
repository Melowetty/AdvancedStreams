package com.melowetty.advancedstreams.apis;

import com.melowetty.advancedstreams.AdvancedStreams;

public class VKAPI extends AbstractAPI {
    public VKAPI(String id) {
        this.API_key = AdvancedStreams.getInstance().getSettingsManager().getVKKey();
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
