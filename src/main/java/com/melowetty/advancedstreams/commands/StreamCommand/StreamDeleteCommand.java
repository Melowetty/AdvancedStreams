package com.melowetty.advancedstreams.commands.StreamCommand;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.commands.BaseSubCommand;
import com.melowetty.advancedstreams.enums.ResponseStatus;
import org.bukkit.entity.Player;

public class StreamDeleteCommand extends BaseSubCommand {
    public StreamDeleteCommand() {
        super("delete", AdvancedStreams.getInstance().getSettingsManager().getMessageStreamDeleteUsage(), 1);
    }

    @Override
    protected void execute(Player sender, String[] args) {
        ResponseStatus status = AdvancedStreams.getInstance().getStreamsManager().deleteStream(args[0]);
        sender.sendMessage(status.getMessage());
    }
}
