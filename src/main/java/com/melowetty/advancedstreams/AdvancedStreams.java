package com.melowetty.advancedstreams;

import com.melowetty.advancedstreams.Command.StreamsCommand;
import com.melowetty.advancedstreams.Events.ClickInventoryEvent;
import com.melowetty.advancedstreams.Managers.SettingsManager;
import com.melowetty.advancedstreams.Managers.StreamsManager;
import com.melowetty.advancedstreams.Managers.TimersManager;
import com.melowetty.advancedstreams.Utils.Helper;
import com.melowetty.advancedstreams.Utils.InventoryHelper;
import com.melowetty.advancedstreams.Utils.ItemHelper;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class AdvancedStreams extends JavaPlugin {
    private static AdvancedStreams instance;
    private static SettingsManager settingsManager;
    private static StreamsManager streamsManager;
    private static TimersManager timersManager;
    private boolean placeholderConnect;
    private Inventory menu;
    @Override
    public void onEnable() {
        instance = this;
        settingsManager = new SettingsManager();
        streamsManager = new StreamsManager();
        timersManager = new TimersManager();

        initMenu();

//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                streamsManager.refreshBroadcastsInfo();
//            }
//        }.runTaskTimer(this, 0, settingsManager.getCooldownUpdateBroadcastInfo() * 20L);

        placeholderConnect = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

        this.getCommand("stream").setExecutor(new StreamsCommand());

        Bukkit.getPluginManager().registerEvents(new ClickInventoryEvent(),this);

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getNameMenu() {
        return menu.getName();
    }

    public static AdvancedStreams getInstance() {
        return instance;
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
    public void initMenu() {
        try {
            String title = Helper.formatWithPlaceholder(settingsManager.getMenuTitle(),"%broadcasts-count%", String.valueOf(streamsManager.getAllStreams().size()));
            int size = settingsManager.getMenuSize();
            menu = InventoryHelper.builder(title, size)
                    .withItems(settingsManager.getHelperItems())
                    .withItems(settingsManager.getFillingItems())
                    .build();
        }
        catch (Exception e) {
            Helper.debug(e);
        }
    }
    public void refreshMenu() {
        InventoryHelper.fill(menu, settingsManager.getItemStreams());
    }

    public Inventory getMenu() {
        return menu;
    }

    public void fullRefreshMenu() {
        initMenu();
        refreshMenu();
    }
}
