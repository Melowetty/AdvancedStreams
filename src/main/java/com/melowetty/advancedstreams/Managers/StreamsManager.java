package com.melowetty.advancedstreams.Managers;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.Stream;
import com.melowetty.advancedstreams.StreamPlatform;

import java.util.*;

public class StreamsManager {
    private AdvancedStreams plugin;
    public StreamsManager() {
        plugin = AdvancedStreams.getInstance();
    }
    private final int maxCountStreams = plugin.getSettingsManager().getMaxStreamsCount();
    private final HashMap<String, Stream> streams = new HashMap<>();
    public HashMap<String, Stream> getStreams() {
        return streams;
    }
    public List<Stream> getAllStreams() {
        return new ArrayList<>(streams.values());
    }
    public void addStream(final String youtuber, final String link, final StreamPlatform streamPlatform) {
        Stream stream = new Stream(youtuber, link, streamPlatform);
        streams.put(stream.getYoutuber(),stream);

    }
    public void deleteStream(String youtuber) {
        streams.remove(youtuber);
    }
    public Stream getStream(String youtuber) {
        return streams.get(youtuber);
    }
    public void sortStreamByViewers(List<Stream> streams) {
        streams.sort((stream1, stream2) -> {
            Integer o1 = stream1.getViewers();
            Integer o2 = stream2.getViewers();

            return o2.compareTo(o1);
        });
    }
    public void sortStreamsByDuration(List<Stream> streams) {
        streams.sort((stream1, stream2) -> stream1.getDuration().compareTo(stream2.getDuration()) * -1);
    }
}
