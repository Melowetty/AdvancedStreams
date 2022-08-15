package com.melowetty.advancedstreams.commands.StreamCommand;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.commands.BaseSubCommand;
import com.melowetty.advancedstreams.enums.Permission;
import com.melowetty.advancedstreams.enums.ResponseStatus;
import com.melowetty.advancedstreams.enums.StreamPlatform;
import com.melowetty.advancedstreams.utils.Helper;
import org.bukkit.entity.Player;

public class TwitchAddCommand extends BaseSubCommand {
    public TwitchAddCommand() {
        super("twitch", Permission.STREAM_ADD_TWITCH, AdvancedStreams.getInstance().getSettingsManager().getMessageTwitchUsage(), 1);
    }

    @Override
    public void execute(Player sender, String[] args) {
        if (AdvancedStreams.getInstance().getSettingsManager().isTwitchConfigured()) {
            try {
                String streamer = Helper.getTwitchAccount(args[0]);
                ResponseStatus status = AdvancedStreams.getInstance().getStreamsManager().addStream(sender, streamer, StreamPlatform.TWITCH);
                sender.sendMessage(status.getMessage());
            } catch (NullPointerException | IndexOutOfBoundsException exception) {
                sender.sendMessage(ResponseStatus.INCORRECT_LINK.getMessage());
            } catch (Exception exception) {
                exception.printStackTrace();
                sender.sendMessage(ResponseStatus.UNDEFINED.getMessage());
            }
        }
        else {
            sender.sendMessage(AdvancedStreams.getInstance().getSettingsManager().getMessageThisPlatformNotActive());
        }
    }
}
