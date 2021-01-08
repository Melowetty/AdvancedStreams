package com.melowetty.advancedstreams.managers;

import com.melowetty.advancedstreams.AdvancedStreams;
import org.bukkit.Bukkit;


public class TimersManager {
    private final AdvancedStreams plugin;
    private final StreamsManager streamsManager;
    private final SettingsManager settings;
    public TimersManager() {
        plugin = AdvancedStreams.getInstance();
        streamsManager = plugin.getStreamsManager();
        settings = plugin.getSettingsManager();
        startTimerEveryTenSeconds();
        startTimerEveryTenMinutes();
    }
    public void startTimerEveryTenSeconds() {
        Bukkit.getScheduler().runTaskTimer(plugin, streamsManager::refreshBroadcastsInfo, 0, settings.getCooldownUpdateBroadcastInfo() * 20L);
    }
    public void startTimerEveryTenMinutes() {
        Bukkit.getScheduler().runTaskTimer(plugin, streamsManager::notificationsAboutCurrentBroadcasts, 0,settings.getCooldownAlerts() * 20L);
    }
}
