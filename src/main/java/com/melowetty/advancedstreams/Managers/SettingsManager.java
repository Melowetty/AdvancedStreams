package com.melowetty.advancedstreams.Managers;

import com.melowetty.advancedstreams.AdvancedStreams;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class SettingsManager {
    private final AdvancedStreams plugin;

    private final File main;

    private final FileConfiguration config;

    private String language;
    private String twitch_key;
    private String youtube_key;
    private String vk_key;

    private int max_streams;
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

        language = getConfig().getString("generals.language", "");

        max_streams = getConfig().getInt("settings-streams.max-count-streams");


        twitch_key = getConfig().getString("apis.twitch");
        youtube_key = getConfig().getString("apis.youtube");
        vk_key = getConfig().getString("apis.vk");

        save();
    }
    public void save() {
        try {
            getConfig().save(main);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getConfig() {
        return config;
    }
    public Locale getLanguage() {
        String[] split = language.split("_");

        if (split.length == 2) {
            return new Locale(split[0], split[1]);
        }

        return new Locale(language);
    }

    public String getYouTubeKey() {
        return youtube_key;
    }

    public String getTwitchKey() {
        return twitch_key;
    }

    public String getVЛKey() {
        return vk_key;
    }

    public int getMaxStreamsCount() {
        return max_streams;
    }
}
