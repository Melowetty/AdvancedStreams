package com.melowetty.advancedstreams.apis;

import com.melowetty.advancedstreams.Stream;
import com.melowetty.advancedstreams.enums.StreamPlatform;

public class APITransponder {
    private Stream stream;
    private StreamPlatform platform;
    private String streamId;
    private String ownerId;
    public APITransponder(Stream stream) {
        this.stream = stream;
        this.platform = stream.getPlatform();
        this.streamId = stream.getID();
        if(platform == StreamPlatform.VK)
            ownerId = stream.getOwnerID();
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
                YouTubeAPI youTubeAPI = new YouTubeAPI(streamId);
                return youTubeAPI.getTitle();
            case TWITCH:
                TwitchAPI twitchAPI = new TwitchAPI(streamId);
                return twitchAPI.getTitle();
            case VK:
                VKAPI vkAPI = new VKAPI(ownerId, streamId);
                return vkAPI.getTitle();
        }
        return null;
    }
    public int getViewers() {
        switch (platform) {
            case YOUTUBE:
                YouTubeAPI youTubeAPI = new YouTubeAPI(streamId);
                if(youTubeAPI.getViewers() != 0) {
                    return youTubeAPI.getViewers();
                }
                return -1;
            case TWITCH:
                TwitchAPI twitchAPI = new TwitchAPI(streamId);
                if(twitchAPI.getViewers() != 0) {
                    return twitchAPI.getViewers();
                }
                return -1;
            case VK:
                VKAPI vkAPI = new VKAPI(ownerId, streamId);
                if(vkAPI.getViewers() != 0) {
                    return vkAPI.getViewers();
                }
                return -1;
        }
        return 0;
    }
    public Long getDuration() {
        switch (platform) {
            case YOUTUBE:
                YouTubeAPI youTubeAPI = new YouTubeAPI(streamId);
                if(youTubeAPI.getDuration() != null) {
                    return youTubeAPI.getDuration();
                }
                return null;
            case TWITCH:
                TwitchAPI twitchAPI = new TwitchAPI(streamId);
                if(twitchAPI.getViewers() != 0) {
                    return twitchAPI.getDuration();
                }
                return null;
            case VK:
                VKAPI vkAPI = new VKAPI(ownerId, streamId);
                if(vkAPI.getViewers() != 0) {
                    return vkAPI.getDuration();
                }
                return null;
        }
        return null;
    }
}
