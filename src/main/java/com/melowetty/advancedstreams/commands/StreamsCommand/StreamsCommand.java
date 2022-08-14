package com.melowetty.advancedstreams.commands.StreamsCommand;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.commands.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class StreamsCommand extends BaseCommand {
    public StreamsCommand() {
        super("streams");
    }
    @Override
    public void execute(Player sender, Command cmd, String[] args) {
        sender.openInventory(AdvancedStreams.getInstance().getMenu().getInventory());
    }
}
