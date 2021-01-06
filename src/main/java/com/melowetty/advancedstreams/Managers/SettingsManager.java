package com.melowetty.advancedstreams.Managers;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.CustomColor;
import com.melowetty.advancedstreams.SortType;
import com.melowetty.advancedstreams.Stream;
import com.melowetty.advancedstreams.Utils.Helper;
import com.melowetty.advancedstreams.Utils.ItemHelper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.melowetty.advancedstreams.Utils.Helper.colored;

public final class SettingsManager {
    private final AdvancedStreams plugin;

    private final File main;

    private final FileConfiguration config;

    private SortType sort_type;

    private String twitch_key;
    private String youtube_key;
    private String vk_key;
    private String youtube_name;
    private String twitch_name;
    private String vk_name;
    private String menu_title;

    private List<Integer> pos_broadcasts;
    private HashMap<Integer, ItemStack> helper_items;
    private HashMap<Integer, ItemStack> filling_items;

    private boolean debug_mode = false;

    private int menu_size;
    private Long cooldown_alerts;
    private Long cooldown_update_broadcast_info;

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

        debug_mode = getConfig().getBoolean("debug-mode");
        cooldown_alerts = getConfig().getLong("cooldown-alerts-active-streams");
        cooldown_update_broadcast_info = getConfig().getLong("cooldown-update-broadcast-info");

        twitch_key = getConfig().getString("api-keys.twitch");
        youtube_key = getConfig().getString("api-keys.youtube");
        vk_key = getConfig().getString("api-keys.vk");

        menu_title = colored(getConfig().getString("settings-menu.title"));
        menu_size = getConfig().getInt("settings-menu.size");
        sort_type = SortType.valueOf(getConfig().getString("settings-menu.sort-type"));
        pos_broadcasts = Helper.getPositionBroadcasts(getConfig().getString("settings-menu.slots-broadcasts"));

        youtube_name = colored(getConfig().getString("platform-name.youtube"));
        twitch_name = colored(getConfig().getString("platform-name.twitch"));
        vk_name = colored(getConfig().getString("platform-name.vk"));

        if(cfgToHashMap(getConfig(), "helper-items") != null)
            helper_items = cfgToHashMap(getConfig(), "helper-items");
        if(cfgToHashMap(getConfig(), "filling-items") != null)
            filling_items = cfgToHashMap(getConfig(), "filling-items");

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
        return youtube_key;
    }

    public String getTwitchKey() {
        return twitch_key;
    }

    public String getVKKey() {
        return vk_key;
    }

    public int getMaxStreamsCount() {
        return pos_broadcasts.size();
    }

    public String getVKName() {
        return vk_name;
    }

    public String getYoutubeName() {
        return youtube_name;
    }

    public String getTwitchName() {
        return twitch_name;
    }

    public boolean isDebug() {
        return debug_mode;
    }

    public SortType getSortType() {
        return sort_type;
    }

    public int getMenuSize() {
        return menu_size;
    }

    public String getMenuTitle() {
        return menu_title;
    }

    public Long getCooldownAlerts() {
        return cooldown_alerts;
    }

    public Long getCooldownUpdateBroadcastInfo() {
        return cooldown_update_broadcast_info;
    }

    public HashMap<Integer, ItemStack> getHelperItems() {
        return helper_items;
    }

    public HashMap<Integer, ItemStack> getFillingItems() {
        return filling_items;
    }

    public static HashMap<Integer, ItemStack> cfgToHashMap(FileConfiguration cfg, String section) {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        if(cfg.getConfigurationSection(section).getKeys(false) == null)
            return null;
        for(String key : cfg.getConfigurationSection(section).getKeys(false))
        {
            ItemHelper.Builder item = ItemHelper.builder(ItemHelper.parseMaterial(cfg.getString(section + "." + key + ".material")));
            String title = Helper.colored(cfg.getString(section + "." + key + ".title"));
            boolean enchanted = cfg.getBoolean(section + "." + key + ".enchantment");
            List <String> lore = Helper.colored(cfg.getStringList(section + "." + key + ".lore"));
            int meta_id = cfg.getInt(section + "." + key + ".meta-id");
            if(!title.isEmpty())
                item.withName(title);
            if(!lore.isEmpty())
                item.withLore(lore);
            if(enchanted) {
                item.addEnchant(Enchantment.DIG_SPEED, 1);
                item.addFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if(meta_id != 0)
                item.withDamage(meta_id);
            if(Helper.getColorFromString(cfg.getString(section + "." + key + ".color")) != CustomColor.NONE) {
                item.withColor(Helper.getColorFromString(cfg.getString(section + "." + key + ".color")));
            }
            if(section.equalsIgnoreCase("filling-items"))
                Helper.cloneAndUnionList(items, Helper.getPositionBroadcasts(cfg.getString(section + "." + key + ".slots")),item.build());
            else
                items.put(cfg.getInt(section + "." + key + ".slot")-1, item.build());
        }
        return items;
    }

    public ItemStack buildItemStream(Stream stream) {
        ItemHelper.Builder item = ItemHelper.builder(
                ItemHelper.parseMaterial(getConfig().getString("format-item-broadcast.material")));
        HashMap<String, String> placeholders = Helper.getPlaceholders(stream);
        String title = Helper.colored(Helper.formatStringWithPlaceholder(
                getConfig().getString("format-item-broadcast.title"),
                placeholders));
        List <String> lore = Helper.formatListWithPlaceholder(
                Helper.colored(getConfig().getStringList("format-item-broadcast.lore")),
                placeholders);
        boolean enchanted = getConfig().getBoolean("format-item-broadcast.enchantment");
        int meta_id = getConfig().getInt("format-item-broadcast.meta-id");
        if(!title.isEmpty())
            item.withName(title);
        if(!lore.isEmpty())
            item.withLore(lore);
        if(enchanted) {
            item.addEnchant(Enchantment.DIG_SPEED, 1);
            item.addFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if(meta_id != 0)
            item.withDamage(meta_id);
        if(Helper.getColorFromString(getConfig().getString("format-item-broadcast.color")) != CustomColor.NONE) {
            item.withColor(Helper.getColorFromString(getConfig().getString("format-item-broadcast.color")));
        }
        return item.build();
    }

    public HashMap<Integer, ItemStack> getItemStreams() {
        HashMap<Integer, ItemStack> out = new HashMap<>();
        StreamsManager manager = AdvancedStreams.getInstance().getStreamsManager();
        List<Stream> streams = manager.getAllStreams();
        manager.sort(streams, getSortType());
        for(int i = 0; i < streams.size(); i++) {
            out.put(pos_broadcasts.get(i), buildItemStream(streams.get(i)));
        }
        return out;
    }
}
