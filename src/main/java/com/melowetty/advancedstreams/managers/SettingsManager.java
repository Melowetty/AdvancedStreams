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
    private String messageSuccessfulAdded;
    private String messageSuccessfulDeleted;
    private String messageNotFound;
    private String messageOverflow;
    private String messageIncorrectLink;
    private String messageUndefined;
    private String messageThisPlatformNotActive;
    private List<String> messageStreamUsage;
    private List<String> messageYoutubeUsage;
    private List<String> messageVkUsage;
    private List<String> messageTwitchUsage;
    private List<String> messageStreamDeleteUsage;
    private String messageNotEnoughPermission;

    private List<Integer> broadcastsPos;
    private HashMap<Integer, ItemStack> helperItems;
    private HashMap<Integer, ItemStack> fillingItems;

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

        cooldownAlerts = getConfig().getLong("cooldown-alerts-active-streams");
        cooldownUpdateBroadcastInfo = getConfig().getLong("cooldown-update-broadcast-info");

        twitchAccessToken = getConfig().getString("api-keys.twitch.access-token", "");
        twitchClientId = getConfig().getString("api-keys.twitch.client-id", "");
        youtubeKey = getConfig().getString("api-keys.youtube", "");
        vkKey = getConfig().getString("api-keys.vk", "");

        menuTitle = Helper.colored(getConfig().getString("settings-menu.title"));
        menuRows = getConfig().getInt("settings-menu.rows");
        sortType = SortType.valueOf(getConfig().getString("settings-menu.sort-type"));
        broadcastsPos = Helper.getPositionBroadcasts(getConfig().getString("settings-menu.slots-broadcasts"));

        youtubeName = Helper.colored(getConfig().getString("platform-name.youtube", "&cYou&fTube"));
        twitchName = Helper.colored(getConfig().getString("platform-name.twitch", "&dTwitch"));
        vkName = Helper.colored(getConfig().getString("platform-name.vk", "&3ВКонтакте"));

        streamMaterial = ItemHelper.parseMaterial(getConfig().getString("format-item-broadcast.material"));
        lengthTitle = getConfig().getInt("format-item-broadcast.max-length-title");

        if(Helper.cfgToHashMap(getConfig(), "helper-items") != null)
            helperItems = Helper.cfgToHashMap(getConfig(), "helper-items");
        if(Helper.cfgToHashMap(getConfig(), "filling-items") != null)
            fillingItems = Helper.cfgToHashMap(getConfig(), "filling-items");

        messageSuccessfulAdded = Helper.colored(getConfig().getString("messages.successful_added", "&aAdvancedStreams &8| &f Стрим был успешно добавлен!"));
        messageSuccessfulDeleted = Helper.colored(getConfig().getString("messages.successful_deleted", "&aAdvancedStreams &8| &f Стрим был успешно удалён!"));
        messageNotFound = Helper.colored(getConfig().getString("messages.not_found", "&aAdvancedStreams &8| &f Стрим не найден!"));
        messageOverflow = Helper.colored(getConfig().getString("messages.overflow", "&aAdvancedStreams &8| &f Нельзя больше добавлять стримы!"));
        messageIncorrectLink = Helper.colored(getConfig().getString("messages.incorrect_link", "&aAdvancedStreams &8| &f Неккоректная ссылка!"));
        messageUndefined = Helper.colored(getConfig().getString("messages.undefined", "&aAdvancedStreams &8| &f Произошла неопределенная ошибка!"));
        messageThisPlatformNotActive = Helper.colored(getConfig().getString("messages.this_platform_not_active", "&aAdvancedStreams &8| &f Эта платформа неактивна!"));
        messageNotEnoughPermission = Helper.colored(getConfig().getString("messages.not_enough_permission", "&aAdvancedStreams &8| &f Недостаточно прав!"));

        messageStreamUsage = Helper.colored(getConfig().getStringList("messages.commands.stream.usage"));
        messageYoutubeUsage = Helper.colored(getConfig().getStringList("messages.commands.stream.youtube.usage"));
        messageVkUsage = Helper.colored(getConfig().getStringList("messages.commands.stream.vk.usage"));
        messageTwitchUsage = Helper.colored(getConfig().getStringList("messages.commands.stream.twitch.usage"));
        messageStreamDeleteUsage = Helper.colored(getConfig().getStringList("messages.commands.stream.delete.usage"));

        save();
    }
    public void save() {
        try {
            getConfig().save(main);
        } catch (IOException e) {
            Helper.log(e);
        }
    }

    public int getMaxStreamsCount() {
        return broadcastsPos.size();
    }
    public boolean isApiKeysConfigured() {
        return twitchAccessToken.isEmpty() && youtubeKey.isEmpty() && vkKey.isEmpty();
    }
    public boolean isVkConfigured() {
        return !(vkKey.isEmpty() && vkName.isEmpty());
    }
    public boolean isYoutubeConfigured() {
        return !(youtubeKey.isEmpty() && youtubeName.isEmpty());
    }
    public boolean isTwitchConfigured() {
        return !(twitchAccessToken.isEmpty() && twitchClientId.isEmpty() && twitchName.isEmpty());
    }
}
