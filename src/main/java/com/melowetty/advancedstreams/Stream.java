package com.melowetty.advancedstreams;

import com.melowetty.advancedstreams.APIs.YouTubeAPI;
import com.melowetty.advancedstreams.Utils.Helper;

public class Stream {
    private String  title;
    private String youtuber;
    private String id;
    private StreamPlatform platform;
    private Long duration;
    private int viewers;
    public Stream(final String youtuber, final String id, final StreamPlatform platform) {
        this.youtuber = youtuber;
        this.id = id;
        this.platform = platform;
        this.title = firstGetTitle();
        regetDuration();
        regetViewers();
    }
    public StreamPlatform getPlatform() {
        return platform;
    }
    public String firstGetTitle() {
        switch (platform) {
            case YOUTUBE:
                YouTubeAPI youTubeAPI = new YouTubeAPI();
                return youTubeAPI.getTitle(id);
            default:
                return null;
        }
    }
    public String getYoutuber() {
        return youtuber;
    }

    public String getID() {
        return id;
    }

    public String getTitle() { return title; }

    public int regetViewers() {
        switch (platform) {
            case YOUTUBE:
                YouTubeAPI youTubeAPI = new YouTubeAPI();
                this.viewers = youTubeAPI.getViewers(id);
                return viewers;
            default:
                return 0;
        }
    }
    public Long regetDuration() {
        switch (platform) {
            case YOUTUBE:
                YouTubeAPI youTubeAPI = new YouTubeAPI();
                this.duration = youTubeAPI.getDuration(id);
                return duration;
            default:
                return null;
        }
    }
    public String getFormatedDuration() {
        return Helper.formatDurationToString(duration);
    }

    public int getViewers() {
        return viewers;
    }

    public Long getDuration() {
        return duration;
    }
}
