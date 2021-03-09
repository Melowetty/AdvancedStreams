package com.melowetty.advancedstreams.events;

import com.melowetty.advancedstreams.Stream;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AddStreamEvent extends Event {
    Stream stream;

    public AddStreamEvent(Stream stream) {
        this.stream = stream;
    }
    private static final HandlerList handlers = new HandlerList();

    public Stream getStream() {
        return stream;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public HandlerList getHandlerList() {
        return handlers;
    }
}
