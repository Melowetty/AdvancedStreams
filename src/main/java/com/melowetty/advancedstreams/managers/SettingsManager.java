package com.melowetty.advancedstreams.managers;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.enums.SortType;
import com.melowetty.advancedstreams.utils.Helper;
import com.melowetty.advancedstreams.utils.ItemHelper;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Getter
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

    private boolean isDebug = false;

    private int menuRows;
    private int lengthTitle;
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

        isDebug = getConfig().getBoolean("debug-mode");
        cooldownAlerts = getConfig().getLong("cooldown-alerts-active-streams");
        cooldownUpdateBroadcastInfo = getConfig().getLong("cooldown-update-broadcast-info");

        twitchAccessToken = getConfig().getString("api-keys.twitch.access-token");
        twitchClientId = getConfig().getString("api-keys.twitch.client-id");
        youtubeKey = getConfig().getString("api-keys.youtube");
        vkKey = getConfig().getString("api-keys.vk");

        menuTitle = Helper.colored(getConfig().getString("settings-menu.title"));
        menuRows = getConfig().getInt("settings-menu.rows");
        sortType = SortType.valueOf(getConfig().getString("settings-menu.sort-type"));
        broadcastsPos = Helper.getPositionBroadcasts(getConfig().getString("settings-menu.slots-broadcasts"));

        youtubeName = Helper.colored(getConfig().getString("platform-name.youtube"));
        twitchName = Helper.colored(getConfig().getString("platform-name.twitch"));
        vkName = Helper.colored(getConfig().getString("platform-name.vk"));

        streamMaterial = ItemHelper.parseMaterial(getConfig().getString("format-item-broadcast.material"));
        lengthTitle = getConfig().getInt("format-item-broadcast.max-length-title");

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

    public int getMaxStreamsCount() {
        return broadcastsPos.size();
    }

}
