package com.melowetty.advancedstreams.apis;

public abstract class AbstractAPI {
    public String ApiKey;
    public String id;
    public String ownerId;
    public abstract Long getDuration();
    public abstract int getViewers();
    public abstract String getTitle();
}
