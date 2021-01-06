package com.melowetty.advancedstreams;

import com.melowetty.advancedstreams.APIs.YouTubeAPI;
import com.melowetty.advancedstreams.Utils.Helper;
import org.bukkit.entity.Player;

public class Stream {
    private final String title;
    private final Player youtuber;
    private final String id;
    private final StreamPlatform platform;
    private Long duration;
    private int viewers;
    public Stream(final Player youtuber, final String id, final String title, final StreamPlatform platform) {
        this.youtuber = youtuber;
        this.id = id;
        this.platform = platform;
        this.title = title;
        regetDuration();
        regetViewers();
    }
    public StreamPlatform getPlatform() {
        return platform;
    }
    public Player getYouTuber() {
        return youtuber;
    }

    public String getID() {
        return id;
    }

    public String getTitle() { return title; }

    public int regetViewers() {
        switch (platform) {
            case YOUTUBE:
                YouTubeAPI youTubeAPI = new YouTubeAPI(id);
                if(youTubeAPI.getViewers() != 0) {
                    return youTubeAPI.getViewers();
                }
                return -1;
            default:
                return -1;
        }
    }
    public Long regetDuration() {
        switch (platform) {
            case YOUTUBE:
                YouTubeAPI youTubeAPI = new YouTubeAPI(id);
                if(youTubeAPI.getDuration() != null) {
                    return youTubeAPI.getDuration();
                }
                return -1L;
            default:
                return null;
        }
    }
    public String getFormatedDuration() {
        return Helper.formatDuration(duration);
    }

    public int getViewers() {
        return viewers;
    }

    public Long getDuration() {
        return duration;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Stream{" +
                "title='" + title + '\'' +
                ", youtuber='" + youtuber + '\'' +
                ", id='" + id + '\'' +
                ", platform=" + platform.toString()+
                ", duration=" + duration +
                ", viewers=" + viewers +
                '}';
    }
}
