package com.melowetty.advancedstreams.enums;

import com.melowetty.advancedstreams.AdvancedStreams;

public enum StreamPlatform {
    YOUTUBE(AdvancedStreams.getInstance().getSettingsManager().getYoutubeName()),
    TWITCH(AdvancedStreams.getInstance().getSettingsManager().getTwitchName()),
    VK(AdvancedStreams.getInstance().getSettingsManager().getVkName());
    private final String name;
    StreamPlatform(String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
