package com.melowetty.advancedstreams.commands;

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
    private List<String> usage = new ArrayList<>();
    private int argsCount = 0;
    public BaseCommand(String command) {
        this.command = command;
    }
    public BaseCommand(String command, List<String> usage, int argsCount) {
        this.command = command;
        this.usage = Helper.colored(usage);
        this.argsCount = argsCount;
    }
    public BaseCommand(String command, String usage, int argsCount) {
        this(command, Collections.singletonList(usage), argsCount);
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        if (command.equalsIgnoreCase(cmd.getName())) {
            if (commandSender instanceof Player) {
                Player sender = (Player) commandSender;
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
