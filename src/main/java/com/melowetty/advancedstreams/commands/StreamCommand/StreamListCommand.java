package com.melowetty.advancedstreams.commands.StreamCommand;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.commands.BaseSubCommand;
import org.bukkit.entity.Player;

public class StreamListCommand extends BaseSubCommand {
    public StreamListCommand() {
        super("list");
    }
    @Override
    public void execute(Player sender, String[] args) {
        sender.openInventory(AdvancedStreams.getInstance().getMenu().getInventory());
    }
}
