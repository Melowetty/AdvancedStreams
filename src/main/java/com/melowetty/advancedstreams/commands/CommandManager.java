package com.melowetty.advancedstreams.commands;

import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager {
    private JavaPlugin plugin;
    private static CommandManager instance;
    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
        instance = this;
    }
    public void addCommand(BaseCommand command) {
        plugin.getCommand(command.getCommand()).setExecutor(command);
    }
    public void addCommand(BaseCommandWithSubCommands command) {
        plugin.getCommand(command.getCommand()).setExecutor(command);
    }
    public static CommandManager getInstance() {
        return instance;
    }
}
