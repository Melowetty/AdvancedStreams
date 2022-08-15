package com.melowetty.advancedstreams.commands;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.enums.Permission;
import com.melowetty.advancedstreams.utils.Helper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseCommand implements CommandExecutor, TabCompleter {
    private final String command;
    private String permission = "";
    private List<String> usage = new ArrayList<>();
    private int argsCount = 0;
    public BaseCommand(String command) {
        this.command = command;
    }
    public BaseCommand(String command, Permission permission) {
        this.command = command;
        this.permission = permission.getBukkitPermission();
    }
    public BaseCommand(String command, Permission permission, List<String> usage, int argsCount) {
        this.command = command;
        this.permission = permission.getBukkitPermission();
        this.usage = Helper.colored(usage);
        this.argsCount = argsCount;
    }
    public BaseCommand(String command, Permission permission, String usage, int argsCount) {
        this(command, permission, Collections.singletonList(usage), argsCount);
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        if (command.equalsIgnoreCase(cmd.getName())) {
            if (commandSender instanceof Player) {
                Player sender = (Player) commandSender;
                if(!permission.isEmpty() && !sender.hasPermission(permission)) {
                    sender.sendMessage(AdvancedStreams.getInstance().getSettingsManager().getMessageNotEnoughPermission());
                    return true;
                }
                if(args.length < argsCount) {
                    for(String message : usage) {
                        sender.sendMessage(message);
                    }
                }
                else {
                    execute(sender, cmd, args);
                }
            }
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command cmd, String s, String[] strings) {
        return null;
    }
    public String getCommand() {
        return command;
    }
    protected abstract void execute(Player sender, Command cmd, String[] args);
}
