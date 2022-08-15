package com.melowetty.advancedstreams.commands.StreamCommand;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.commands.BaseCommandWithSubCommands;
import com.melowetty.advancedstreams.enums.Permission;

public class StreamCommand extends BaseCommandWithSubCommands {
    public StreamCommand() {
        super("stream", Permission.STREAM_LIST, AdvancedStreams.getInstance().getSettingsManager().getMessageStreamUsage());
        super.addSubCommands(new YoutubeAddCommand());
        super.addSubCommands(new TwitchAddCommand());
        super.addSubCommands(new VkAddCommand());
        super.addSubCommands(new StreamListCommand());
        super.addSubCommands(new StreamDeleteCommand());
    }
}
