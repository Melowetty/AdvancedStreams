package com.melowetty.advancedstreams.apis;

import com.melowetty.advancedstreams.Stream;
import com.melowetty.advancedstreams.enums.StreamPlatform;

public class APITransponder {
    private final StreamPlatform platform;
    private final String streamId;
    private String ownerId;
    public APITransponder(Stream stream) {
        this.platform = stream.getPlatform();
        this.streamId = stream.getId();
        if(platform == StreamPlatform.VK)
            ownerId = stream.getOwnerId();
    }
    public APITransponder(StreamPlatform platform, String id) {
        this.platform = platform;
        this.streamId = id;
    }
    public APITransponder(StreamPlatform platform, String ownerId, String id) {
        this.platform = platform;
        this.streamId = id;
        this.ownerId = ownerId;
    }
    public String getTitle() {
        switch (platform) {
            case YOUTUBE:
                YouTubeAPI youTubeApi = new YouTubeAPI(streamId);
                return youTubeApi.getTitle();
            case TWITCH:
                TwitchAPI twitchApi = new TwitchAPI(streamId);
                return twitchApi.getTitle();
            case VK:
                VKAPI vkApi = new VKAPI(ownerId, streamId);
                return vkApi.getTitle();
        }
        return null;
    }
    public int getViewers() {
        switch (platform) {
            case YOUTUBE:
                YouTubeAPI youTubeApi = new YouTubeAPI(streamId);
                if(youTubeApi.getViewers() != 0) {
                    return youTubeApi.getViewers();
                }
                return -1;
            case TWITCH:
                TwitchAPI twitchApi = new TwitchAPI(streamId);
                if(twitchApi.getViewers() != 0) {
                    return twitchApi.getViewers();
                }
                return -1;
            case VK:
                VKAPI vkApi = new VKAPI(ownerId, streamId);
                if(vkApi.getViewers() != 0) {
                    return vkApi.getViewers();
                }
                return -1;
        }
        return 0;
    }
    public Long getDuration() {
        switch (platform) {
            case YOUTUBE:
                YouTubeAPI youTubeApi = new YouTubeAPI(streamId);
                if(youTubeApi.getDuration() != null) {
                    return youTubeApi.getDuration();
                }
                return null;
            case TWITCH:
                TwitchAPI twitchApi = new TwitchAPI(streamId);
                if(twitchApi.getViewers() != 0) {
                    return twitchApi.getDuration();
                }
                return null;
            case VK:
                VKAPI vkApi = new VKAPI(ownerId, streamId);
                if(vkApi.getViewers() != 0) {
                    return vkApi.getDuration();
                }
                return null;
        }
        return null;
    }
}
