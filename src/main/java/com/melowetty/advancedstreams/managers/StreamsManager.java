package com.melowetty.advancedstreams.managers;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.Stream;
import com.melowetty.advancedstreams.apis.APITransponder;
import com.melowetty.advancedstreams.enums.RemoveReason;
import com.melowetty.advancedstreams.enums.ResponseStatus;
import com.melowetty.advancedstreams.enums.SortType;
import com.melowetty.advancedstreams.enums.StreamPlatform;
import com.melowetty.advancedstreams.events.AddStreamEvent;
import com.melowetty.advancedstreams.events.RemoveStreamEvent;
import com.melowetty.advancedstreams.events.UpdateStreamsEvent;
import com.melowetty.advancedstreams.utils.ChatHelper;
import com.melowetty.advancedstreams.utils.Helper;
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

    public List<String> getListStreams() {
        List<String> out = new ArrayList<>();
        for(Stream stream : getAllStreams()) {
            out.add(stream.toString());
        }
        return out;
    }

    public ResponseStatus addStream(final Player youtuber, final String id, final StreamPlatform streamPlatform) {
        if(AdvancedStreams.getInstance().getSettingsManager().getMaxStreamsCount() == streams.size())
            return ResponseStatus.OVERFLOW;

        String title = new APITransponder(streamPlatform, id).getTitle();

        if(title == null)
            return ResponseStatus.ERROR;

        title = Helper.formatTitle(title, plugin.getSettingsManager());

        Stream stream = new Stream(youtuber, id, title, streamPlatform);
        streams.put(youtuber.getName().toLowerCase(),stream);
        plugin.fullRefreshMenu();
        AddStreamEvent event = new AddStreamEvent(stream);
        Bukkit.getServer().getPluginManager().callEvent(event);
        sortedStreams = Helper.getSortedStreams();
        return ResponseStatus.SUCCESS;
    }

    public ResponseStatus addStream(final Player youtuber, final String ownerId, final String id, final StreamPlatform streamPlatform) {
        if(AdvancedStreams.getInstance().getSettingsManager().getMaxStreamsCount() == streams.size())
            return ResponseStatus.OVERFLOW;

        String title = new APITransponder(streamPlatform, ownerId, id).getTitle();

        if(title == null)
            return ResponseStatus.ERROR;

        title = Helper.formatTitle(title, plugin.getSettingsManager());

        Stream stream = new Stream(youtuber, ownerId, id, title, streamPlatform);
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
        if(streams.size() == 0) return;
        for (Player player: plugin.getServer().getOnlinePlayers()) {
            ChatHelper.sendMessage(player, "&fAdvancedStreams &8> &aНа сервере идёт " + streams.size() + " стрим! Напишите &a&l/streams &aдля просмотра");
        }
    }
    public void refreshBroadcastsInfo() {
        int deletedStreams = 0;
        for(Stream stream : getAllStreams()) {
            APITransponder API = new APITransponder(stream);
            if(API.getViewers() == 0 || API.getDuration() == null) {
                deletedStreams++;
                streams.remove(stream.getYouTuber().getName());
                RemoveStreamEvent event = new RemoveStreamEvent(stream, RemoveReason.END);
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
            else {
                stream.setViewers(API.getViewers());
                stream.setDuration(API.getDuration());
            }
        }
        if(deletedStreams > 0)
            plugin.fullRefreshMenu();
        else if(!getAllStreams().isEmpty())
            plugin.refreshMenu();

        UpdateStreamsEvent event = new UpdateStreamsEvent(getAllStreams());
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public Stream getStreamWithSlot(int slot) {
        return sortedStreams.get(slot);
    }
}
