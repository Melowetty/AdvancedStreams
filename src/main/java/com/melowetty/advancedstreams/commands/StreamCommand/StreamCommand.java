package com.melowetty.advancedstreams.commands.StreamCommand;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.commands.BaseCommandWithSubCommands;

public class StreamCommand extends BaseCommandWithSubCommands {
    public StreamCommand() {
        super("stream", AdvancedStreams.getInstance().getSettingsManager().getMessageStreamUsage());
        super.addSubCommands(new YoutubeAddCommand());
        super.addSubCommands(new TwitchAddCommand());
        super.addSubCommands(new VkAddCommand());
        super.addSubCommands(new StreamListCommand());
        super.addSubCommands(new StreamDeleteCommand());
    }
}
