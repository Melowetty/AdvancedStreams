package com.melowetty.advancedstreams.managers;

import com.melowetty.advancedstreams.*;
import com.melowetty.advancedstreams.events.AddStreamEvent;
import com.melowetty.advancedstreams.events.RemoveStreamEvent;
import com.melowetty.advancedstreams.utils.Helper;
import com.melowetty.advancedstreams.utils.URLHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StreamsManager {
    private final AdvancedStreams plugin;
    public StreamsManager() {
        plugin = AdvancedStreams.getInstance();
    }
    private final HashMap<String, Stream> streams = new HashMap<>();
    private HashMap<Integer, Stream> sortedStreams = new HashMap<>();
    public HashMap<String, Stream> getStreams() {
        return streams;
    }
    public List<Stream> getAllStreams() {
        return new ArrayList<>(streams.values());
    }
    public ResponseStatus addStream(final Player youtuber, final String link, final StreamPlatform streamPlatform) {
        if(AdvancedStreams.getInstance().getSettingsManager().getMaxStreamsCount() == streams.size())
            return ResponseStatus.OVERFLOW;
        String title = URLHelper.getTitle(streamPlatform, link);
        if(title == null)
            return ResponseStatus.ERROR;
        Stream stream = new Stream(youtuber, link, title, streamPlatform);
        streams.put(youtuber.getName().toLowerCase(),stream);
        plugin.fullRefreshMenu();
        AddStreamEvent event = new AddStreamEvent(stream);
        Bukkit.getServer().getPluginManager().callEvent(event);
        sortedStreams = Helper.getSortedStreams();
        return ResponseStatus.SUCCESS;
    }
    public void deleteStream(String youtuber) {
        Stream stream = streams.get(youtuber);
        streams.remove(youtuber.toLowerCase());
        plugin.fullRefreshMenu();
        RemoveStreamEvent event = new RemoveStreamEvent(stream, RemoveReason.DELETED);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if(streams.size() != 0)
            sortedStreams = Helper.getSortedStreams();

    }
    public Stream getStream(String youtuber) {
        return streams.get(youtuber.toLowerCase());
    }
    private void sortStreamsByViewers(List<Stream> streams) {
        streams.sort((stream1, stream2) -> {
            Integer o1 = stream1.getViewers();
            Integer o2 = stream2.getViewers();

            return o2.compareTo(o1);
        });
    }
    private void sortStreamsByDuration(List<Stream> streams) {
        streams.sort((stream1, stream2) -> stream1.getDuration().compareTo(stream2.getDuration()) * -1);
    }
    public void sort(List<Stream> streams, SortType sort) {
        switch (sort) {
            case BY_VIEWERS:
                sortStreamsByViewers(streams);
                break;
            case BY_DURATION:
                sortStreamsByDuration(streams);
                break;
            case DEFAULT:
        }
    }
    public void notificationsAboutCurrentBroadcasts() {
        // to upgrade
    }
    public void refreshBroadcastsInfo() {
        int deletedStreams = 0;
        for(Stream stream : getAllStreams()) {
            if(stream.regetViewers() == -1 || stream.regetDuration() == -1L) {
                deletedStreams++;
                streams.remove(stream.getYouTuber().getName());
                RemoveStreamEvent event = new RemoveStreamEvent(stream, RemoveReason.END);
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
            else {
                stream.setViewers(stream.regetViewers());
                stream.setDuration(stream.regetDuration());
            }
        }
        if(deletedStreams > 0)
            plugin.fullRefreshMenu();
        else if(!getAllStreams().isEmpty())
            plugin.refreshMenu();
    }

    public Stream getStreamWithSlot(int slot) {
        return sortedStreams.get(slot);
    }
}
