package com.melowetty.advancedstreams.managers;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.enums.SortType;
import com.melowetty.advancedstreams.utils.Helper;
import com.melowetty.advancedstreams.utils.ItemHelper;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public final class SettingsManager {
    private final AdvancedStreams plugin;

    private final File main;

    private final FileConfiguration config;

    private SortType sortType;

    private String twitchAccessToken;
    private String twitchClientId;
    private String youtubeKey;
    private String vkKey;
    private String youtubeName;
    private String twitchName;
    private String vkName;
    private String menuTitle;

    private List<Integer> broadcastsPos;
    private HashMap<Integer, ItemStack> helperItems;
    private HashMap<Integer, ItemStack> fillingItems;

    private boolean debugMode = false;

    private int menuSize;
    private Long cooldownAlerts;
    private Long cooldownUpdateBroadcastInfo;

    private Material streamMaterial;

    public SettingsManager() {
        plugin = AdvancedStreams.getInstance();
        config = plugin.getConfig();
        main = new File(plugin.getDataFolder() + File.separator + "config.yml");
        load();
    }
    public void load() {
        boolean exists = (main).exists();
        if (exists) {
            try {
                getConfig().options().copyDefaults(true);
                getConfig().load(main);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getConfig().options().copyDefaults(true);
        }

        debugMode = getConfig().getBoolean("debug-mode");
        cooldownAlerts = getConfig().getLong("cooldown-alerts-active-streams");
        cooldownUpdateBroadcastInfo = getConfig().getLong("cooldown-update-broadcast-info");

        twitchAccessToken = getConfig().getString("api-keys.twitch.access-token");
        twitchClientId = getConfig().getString("api-keys.twitch.client-id");
        youtubeKey = getConfig().getString("api-keys.youtube");
        vkKey = getConfig().getString("api-keys.vk");

        menuTitle = Helper.colored(getConfig().getString("settings-menu.title"));
        menuSize = getConfig().getInt("settings-menu.size");
        sortType = SortType.valueOf(getConfig().getString("settings-menu.sort-type"));
        broadcastsPos = Helper.getPositionBroadcasts(getConfig().getString("settings-menu.slots-broadcasts"));

        youtubeName = Helper.colored(getConfig().getString("platform-name.youtube"));
        twitchName = Helper.colored(getConfig().getString("platform-name.twitch"));
        vkName = Helper.colored(getConfig().getString("platform-name.vk"));

        streamMaterial = ItemHelper.parseMaterial(getConfig().getString("format-item-broadcast.material"));

        if(Helper.cfgToHashMap(getConfig(), "helper-items") != null)
            helperItems = Helper.cfgToHashMap(getConfig(), "helper-items");
        if(Helper.cfgToHashMap(getConfig(), "filling-items") != null)
            fillingItems = Helper.cfgToHashMap(getConfig(), "filling-items");

        save();
    }
    public void save() {
        try {
            getConfig().save(main);
        } catch (IOException e) {
            Helper.debug(e);
        }
    }
    public FileConfiguration getConfig() {
        return config;
    }

    public String getYouTubeKey() {
        return youtubeKey;
    }

    public String getTwitchAccessToken() {
        return twitchAccessToken;
    }

    public String getTwitchClientID() {
        return twitchClientId;
    }

    public String getVKKey() {
        return vkKey;
    }

    public int getMaxStreamsCount() {
        return broadcastsPos.size();
    }

    public String getVKName() {
        return vkName;
    }

    public String getYoutubeName() {
        return youtubeName;
    }

    public String getTwitchName() {
        return twitchName;
    }

    public boolean isDebug() {
        return debugMode;
    }

    public SortType getSortType() {
        return sortType;
    }

    public int getMenuSize() {
        return menuSize;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public Long getCooldownAlerts() {
        return cooldownAlerts;
    }

    public Long getCooldownUpdateBroadcastInfo() {
        return cooldownUpdateBroadcastInfo;
    }

    public HashMap<Integer, ItemStack> getHelperItems() {
        return helperItems;
    }

    public HashMap<Integer, ItemStack> getFillingItems() {
        return fillingItems;
    }

    public Material getStreamMaterial() {
        return streamMaterial;
    }

    public List<Integer> getBroadcastsPos() {
        return broadcastsPos;
    }
}
