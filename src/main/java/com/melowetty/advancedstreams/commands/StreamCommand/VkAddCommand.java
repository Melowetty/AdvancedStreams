package com.melowetty.advancedstreams.commands.StreamCommand;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.commands.BaseSubCommand;
import com.melowetty.advancedstreams.enums.Permission;
import com.melowetty.advancedstreams.enums.ResponseStatus;
import com.melowetty.advancedstreams.enums.StreamPlatform;
import com.melowetty.advancedstreams.utils.Helper;
import org.bukkit.entity.Player;

public class VkAddCommand extends BaseSubCommand {
    public VkAddCommand() {
        super("vk", Permission.STREAM_ADD_VK, AdvancedStreams.getInstance().getSettingsManager().getMessageVkUsage(), 1);
    }

    @Override
    public void execute(Player sender, String[] args) {
        if(AdvancedStreams.getInstance().getSettingsManager().isVkConfigured()) {
            try {
                String[] ids = Helper.getVKIds(args[0]);
                ResponseStatus status = AdvancedStreams.getInstance().getStreamsManager().addStream(sender, ids[0], ids[1], StreamPlatform.VK);
                sender.sendMessage(status.getMessage());
            } catch (NullPointerException | IndexOutOfBoundsException exception) {
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
