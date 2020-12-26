package com.melowetty.advancedstreams;

import com.melowetty.advancedstreams.Listeners.InventoryClickListener;
import com.melowetty.advancedstreams.Managers.SettingsManager;
import com.melowetty.advancedstreams.Managers.StreamsManager;
import com.melowetty.advancedstreams.Managers.TimersManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedStreams extends JavaPlugin {
    private static AdvancedStreams instance;
    private static SettingsManager settingsManager;
    private static StreamsManager streamsManager;
    private static TimersManager timersManager;
    private boolean placeholderConnect;
    @Override
    public void onEnable() {
        instance = this;
        settingsManager = new SettingsManager();
        streamsManager = new StreamsManager();
        timersManager = new TimersManager();
        connectToPlaceholderAPI();

        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(),this);
        // Plugin startup logic
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static AdvancedStreams getInstance() {
        return instance;
    }
    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
    public void connectToPlaceholderAPI() {
        placeholderConnect = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
    public boolean isPlaceholderConnect() {
        return placeholderConnect;
    }
    public static StreamsManager getStreamsManager() {
        return streamsManager;
    }
}
