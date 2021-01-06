package com.melowetty.advancedstreams;

public enum StreamPlatform {
    YOUTUBE(AdvancedStreams.getInstance().getSettingsManager().getYoutubeName()),
    TWITCH(AdvancedStreams.getInstance().getSettingsManager().getTwitchName()),
    VK(AdvancedStreams.getInstance().getSettingsManager().getVKName());
    private final String name;
    StreamPlatform(String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
