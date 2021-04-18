package com.melowetty.advancedstreams;

import com.melowetty.advancedstreams.apis.APITransponder;
import com.melowetty.advancedstreams.enums.StreamPlatform;
import com.melowetty.advancedstreams.utils.DateHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.entity.Player;

@Getter
@ToString
public class Stream {
    private String ownerId;
    private final String title;
    private final String id;
    @Setter private Long duration;
    @Setter private int viewers;
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

    public Stream(final Player youtuber, final String ownerId, final String id, final String title, final StreamPlatform platform) {
        this.youtuber = youtuber;
        this.id = id;
        this.platform = platform;
        this.title = title;
        this.ownerId = ownerId;
        APITransponder API = new APITransponder(this);
        duration = API.getDuration();
        viewers = API.getViewers();
    }

    public String getFormatedDuration() {
        return DateHelper.formatDuration(duration, platform);
    }

}
