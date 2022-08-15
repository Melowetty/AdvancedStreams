package com.melowetty.advancedstreams.commands;

import com.melowetty.advancedstreams.enums.Permission;
import com.melowetty.advancedstreams.utils.Helper;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseSubCommand {
    private final String command;
    private String permission = "";
    private List<String> usage = new ArrayList<>();
    private int argsCount = 0;
    public BaseSubCommand(String command) {
        this.command = command;
    }
    public BaseSubCommand(String command, String permission) {
        this.command = command;
        this.permission = permission;
    }
    public BaseSubCommand(String command, Permission permission) {
        this(command, permission.getBukkitPermission());
    }
    public BaseSubCommand(String command, List<String> usage, int argsCount) {
        this.command = command;
        this.usage = Helper.colored(usage);
        this.argsCount = argsCount;
    }
    public BaseSubCommand(String command, String usage, int argsCount) {
        this(command, Collections.singletonList(usage), argsCount);
    }
    public BaseSubCommand(String command, Permission permission, String usage, int argsCount) {
        this.command = command;
        this.permission = permission.getBukkitPermission();
        this.usage = Collections.singletonList(usage);
        this.argsCount = argsCount;
    }
    public BaseSubCommand(String command, Permission permission, List<String> usage, int argsCount) {
        this(command, usage, argsCount);
        this.permission = permission.getBukkitPermission();
    }
    protected abstract void execute(Player sender, String[] args);

    public String getCommand() {
        return command;
    }

    public int getArgsCount() {
        return argsCount;
    }

    public List<String> getUsage() {
        return usage;
    }

    public String getPermission() {
        return permission;
    }
}
