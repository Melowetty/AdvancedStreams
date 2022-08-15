package com.melowetty.advancedstreams.managers;

import com.melowetty.advancedstreams.AdvancedStreams;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;


public class TimersManager {
    private final AdvancedStreams plugin;
    private final StreamsManager streamsManager;
    private final SettingsManager settings;
    private BukkitTask timerEveryTenSeconds;
    private BukkitTask timerEveryTenMinutes;
    public TimersManager() {
        plugin = AdvancedStreams.getInstance();
        streamsManager = plugin.getStreamsManager();
        settings = plugin.getSettingsManager();
    }
    public void start() {
        startTimerEveryTenSeconds();
        startTimerEveryTenMinutes();
    }
    public void stop() {
        timerEveryTenSeconds.cancel();
        timerEveryTenMinutes.cancel();
    }
    private void startTimerEveryTenSeconds() {
        timerEveryTenSeconds = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, streamsManager::refreshBroadcastsInfo, 0, settings.getCooldownUpdateBroadcastInfo() * 20L);
    }
    private void startTimerEveryTenMinutes() {
        timerEveryTenMinutes = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, streamsManager::notificationsAboutCurrentBroadcasts, 0,settings.getCooldownAlerts() * 20L);
    }
}
