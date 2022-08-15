package com.melowetty.advancedstreams.commands.StreamCommand;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.commands.BaseSubCommand;
import com.melowetty.advancedstreams.enums.Permission;
import org.bukkit.entity.Player;

public class StreamListCommand extends BaseSubCommand {
    public StreamListCommand() {
        super("list", Permission.STREAM_LIST);
    }
    @Override
    public void execute(Player sender, String[] args) {
        sender.openInventory(AdvancedStreams.getInstance().getMenu().getInventory());
    }
}
