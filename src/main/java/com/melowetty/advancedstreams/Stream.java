package com.melowetty.advancedstreams;

import com.melowetty.advancedstreams.apis.APITransponder;
import com.melowetty.advancedstreams.utils.Helper;
import org.bukkit.entity.Player;

public class Stream {
    private String ownerId;
    private final String title;
    private final String id;
    private Long duration;
    private int viewers;
    private final Player youtuber;
    private final StreamPlatform platform;
    public Stream(final Player youtuber, final String id, final String title, final StreamPlatform platform) {
        this.youtuber = youtuber;
        this.id = id;
        this.platform = platform;
        this.title = title;
        APITransponder API = new APITransponder(this);
        duration = API.getDuration();
        viewers = API.getViewers();
    }
    public Stream(final Player youtuber, final String  ownerId, final String id, final String title, final StreamPlatform platform) {
        this.youtuber = youtuber;
        this.id = id;
        this.platform = platform;
        this.title = title;
        this.ownerId = ownerId;
        APITransponder API = new APITransponder(this);
        duration = API.getDuration();
        viewers = API.getViewers();
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

    public String getOwnerID() {
        return ownerId;
    }

    @Override
    public String toString() {
        return "Stream{" +
                "title='" + title + '\'' +
                ", youtuber='" + youtuber.getDisplayName() + '\'' +
                ", id='" + id + '\'' +
                ", platform=" + platform.toString()+
                ", duration=" + duration +
                ", viewers=" + viewers +
                '}';
    }
}
