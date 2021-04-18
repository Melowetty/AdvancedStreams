package com.melowetty.advancedstreams.events;

import com.melowetty.advancedstreams.Stream;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;


public class UpdateStreamsEvent extends Event {
    List<Stream> streams;

    public UpdateStreamsEvent(List<Stream> streams) {
        this.streams = streams;
    }
    private static final HandlerList handlers = new HandlerList();

    public List<Stream> getStreamList() {
        return streams;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public HandlerList getHandlerList() {
        return handlers;
    }
}
