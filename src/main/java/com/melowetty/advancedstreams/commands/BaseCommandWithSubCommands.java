package com.melowetty.advancedstreams.commands;

import com.melowetty.advancedstreams.utils.Helper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class BaseCommandWithSubCommands implements CommandExecutor, TabCompleter {
    private final String command;
    private List<String> usage = new ArrayList<>();
    private List<BaseSubCommand> subCommands = new ArrayList<>();
    public BaseCommandWithSubCommands(String command) {
        this.command = command;
    }
    public BaseCommandWithSubCommands(String command, List<String> usage) {
        this.command = command;
        this.usage = Helper.colored(usage);
    }
    public BaseCommandWithSubCommands(String command, String usage) {
        this(command, Collections.singletonList(usage));
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        if (command.equalsIgnoreCase(cmd.getName())) {
            if (commandSender instanceof Player) {
                Player sender = (Player) commandSender;
                if(args.length == 0) {
                    sendUsage(sender, usage);
                    return false;
                }
                String sub = args[0];
                for (BaseSubCommand subCommand : subCommands) {
                    if(subCommand.getCommand().equalsIgnoreCase(sub)) {
                        if(args.length - 1 < subCommand.getArgsCount()) {
                            sendUsage(sender, subCommand.getUsage());
                            return false;
                        }
                        else {
                            subCommand.execute(sender, Arrays.copyOfRange(args, 1, args.length));
                            return true;
                        }
                    }
                }
                sendUsage(sender, usage);
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

    public List<BaseSubCommand> getSubCommands() {
        return subCommands;
    }

    public void addSubCommands(BaseSubCommand baseSubCommand) {
        subCommands.add(baseSubCommand);
    }

    private void sendUsage(Player player, List<String> usage) {
        for(String message : usage) {
            player.sendMessage(message);
        }
    }
}
