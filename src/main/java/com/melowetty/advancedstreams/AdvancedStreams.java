package com.melowetty.advancedstreams;

import com.melowetty.advancedstreams.commands.CommandManager;
import com.melowetty.advancedstreams.commands.StreamCommand.StreamCommand;
import com.melowetty.advancedstreams.commands.StreamsCommand.StreamsCommand;
import com.melowetty.advancedstreams.gui.ClickListener;
import com.melowetty.advancedstreams.gui.StreamsInventory;
import com.melowetty.advancedstreams.managers.SettingsManager;
import com.melowetty.advancedstreams.managers.StreamsManager;
import com.melowetty.advancedstreams.managers.TimersManager;
import com.melowetty.advancedstreams.utils.Helper;
import com.melowetty.advancedstreams.utils.InventoryHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class AdvancedStreams extends JavaPlugin {
    private static AdvancedStreams INSTANCE;
    private static SettingsManager settingsManager;
    private static StreamsManager streamsManager;
    private static TimersManager timersManager;
    private static CommandManager commandManager;
    private boolean placeholderConnect;
    private StreamsInventory menu;
    @Override
    public void onEnable() {
        INSTANCE = this;
        settingsManager = new SettingsManager();
        if(settingsManager.isApiKeysConfigured()) {
            Helper.log("Plugin disabled because api keys not configured.");
            Helper.log("Please configure any platform.");
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        streamsManager = new StreamsManager();
        timersManager = new TimersManager();
        commandManager = new CommandManager(this);
        commandManager.addCommand(new StreamsCommand());
        commandManager.addCommand(new StreamCommand());

        initMenu();

        placeholderConnect = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

        Bukkit.getPluginManager().registerEvents(new ClickListener(), this);

        timersManager.start();

    }
    @Override
    public void onDisable() {
        timersManager.stop();
    }

    public String getNameMenu() {
        return menu.getInventory().getName();
    }

    public static AdvancedStreams getInstance() {
        return INSTANCE;
    }
    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
    public boolean isPlaceholderConnect() {
        return placeholderConnect;
    }
    public StreamsManager getStreamsManager() {
        return streamsManager;
    }
    public TimersManager getTimersManager() {
        return timersManager;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public void initMenu() {
        try {
            String title = Helper.formatWithPlaceholder(
                    settingsManager.getMenuTitle(),
                    "%broadcasts-count%",
                    String.valueOf(streamsManager.getAllStreams().size()));
            int rows = settingsManager.getMenuRows();
            menu = InventoryHelper.builder(rows, title)
                    .withItems(settingsManager.getHelperItems())
                    .withItems(settingsManager.getFillingItems())
                    .build();
        }
        catch (Exception e) {
            Helper.debug(e);
        }
    }
    public void refreshMenu() {
        InventoryHelper.fill(menu.getInventory(), Helper.getItemStreams());
    }

    public StreamsInventory getMenu() {
        return menu;
    }

    public void fullRefreshMenu() {
        List<HumanEntity> viewers = menu.getInventory().getViewers();
        initMenu();
        refreshMenu();
        for(HumanEntity player : viewers) {
            player.closeInventory();
            player.openInventory(menu.getInventory());
        }
    }
}
