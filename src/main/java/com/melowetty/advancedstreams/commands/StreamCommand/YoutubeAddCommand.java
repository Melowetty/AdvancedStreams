package com.melowetty.advancedstreams.commands.StreamCommand;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.commands.BaseSubCommand;
import com.melowetty.advancedstreams.enums.Permission;
import com.melowetty.advancedstreams.enums.ResponseStatus;
import com.melowetty.advancedstreams.enums.StreamPlatform;
import com.melowetty.advancedstreams.utils.Helper;
import org.bukkit.entity.Player;

public class YoutubeAddCommand extends BaseSubCommand {
    public YoutubeAddCommand() {
        super("youtube", Permission.STREAM_ADD_YOUTUBE, AdvancedStreams.getInstance().getSettingsManager().getMessageYoutubeUsage(), 1);
    }

    @Override
    public void execute(Player sender, String[] args) {
        if(AdvancedStreams.getInstance().getSettingsManager().isYoutubeConfigured()) {
            try {
                String id = Helper.getYouTubeVideoID(args[0]);
                ResponseStatus status = AdvancedStreams.getInstance().getStreamsManager().addStream(sender, id, StreamPlatform.YOUTUBE);
                sender.sendMessage(status.getMessage());
            } catch (IndexOutOfBoundsException | NullPointerException exception) {
                sender.sendMessage(ResponseStatus.INCORRECT_LINK.getMessage());
            } catch (Exception exception) {
                exception.printStackTrace();
                sender.sendMessage(ResponseStatus.UNDEFINED.getMessage());
            }
        } else {
            sender.sendMessage(AdvancedStreams.getInstance().getSettingsManager().getMessageThisPlatformNotActive());
        }
    }
}
