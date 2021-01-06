package com.melowetty.advancedstreams.APIs;

public abstract class AbstractAPI {
    public String API_key;
    public String id;
    public abstract Long getDuration();
    public abstract int getViewers();
    public abstract String getTitle();
}
