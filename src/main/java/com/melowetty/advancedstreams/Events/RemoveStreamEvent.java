package com.melowetty.advancedstreams.Events;

import com.melowetty.advancedstreams.RemoveReason;
import com.melowetty.advancedstreams.Stream;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RemoveStreamEvent extends Event {
    Stream stream;
    RemoveReason reason;
    public RemoveStreamEvent(Stream stream, RemoveReason reason) {
        this.stream = stream;
        this.reason = reason;
    }
    private static final HandlerList handlers = new HandlerList();

    public Stream getStream() {
        return stream;
    }

    public RemoveReason getReason() {
        return reason;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public HandlerList getHandlerList() {
        return handlers;
    }
}
