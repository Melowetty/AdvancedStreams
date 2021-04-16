package com.melowetty.advancedstreams.utils;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.Stream;
import com.melowetty.advancedstreams.enums.CustomColor;
import com.melowetty.advancedstreams.managers.SettingsManager;
import com.melowetty.advancedstreams.managers.StreamsManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Helper {
    public static String objectToString(Object object) {
        return String.valueOf(object);
    }
    public static int objectToInt(Object object) {
        return Integer.parseInt( String.valueOf(object) );
    }
    public static Long objectToLong(Object object) {
        return Long.parseLong( String.valueOf(object) );
    }
    public static JSONObject parseURL(String URL) {
        return (JSONObject) JSONValue.parse(arrayListToString(getContent(URL)));
    }
    public static JSONObject parseURL(String URL, String accessToken, String clientId) {
        return (JSONObject) JSONValue.parse(arrayListToString(getContent(URL, accessToken, clientId)));
    }
    public static HashMap<Integer, ItemStack> getItemStreams() {
        HashMap<Integer, ItemStack> out = new HashMap<>();
        StreamsManager streamsManager = AdvancedStreams.getInstance().getStreamsManager();
        SettingsManager settingsManager = AdvancedStreams.getInstance().getSettingsManager();
        List<Stream> streams = streamsManager.getAllStreams();
        streamsManager.sort(streams, settingsManager.getSortType());
        for(int i = 0; i < streams.size(); i++) {
            out.put(settingsManager.getBroadcastsPos().get(i), buildItemStream(streams.get(i)));
        }
        return out;
    }
    public static ItemStack buildItemStream(Stream stream) {
        SettingsManager manager = AdvancedStreams.getInstance().getSettingsManager();
        ItemHelper.Builder item = ItemHelper.builder(
                manager.getStreamMaterial());
        HashMap<String, String> placeholders = getPlaceholders(stream);
        String title = colored(formatStringWithPlaceholder(
                manager.getConfig().getString("format-item-broadcast.title"),
                placeholders));
        List <String> lore = formatListWithPlaceholder(
                colored(manager.getConfig().getStringList("format-item-broadcast.lore")),
                placeholders);
        boolean enchanted = manager.getConfig().getBoolean("format-item-broadcast.enchantment");
        int meta_id = manager.getConfig().getInt("format-item-broadcast.meta-id");
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
        if(getColorFromString(manager.getConfig().getString("format-item-broadcast.color")) != CustomColor.NONE) {
            item.withColor(getColorFromString(manager.getConfig().getString("format-item-broadcast.color")));
        }
        return item.build();
    }
    public static HashMap<Integer, Stream> getSortedStreams() {
        HashMap<Integer, Stream> temp = new HashMap<>();
        StreamsManager streamsManager = AdvancedStreams.getInstance().getStreamsManager();
        SettingsManager settingsManager = AdvancedStreams.getInstance().getSettingsManager();
        List<Stream> streams = streamsManager.getAllStreams();
        streamsManager.sort(streams, settingsManager.getSortType());
        for(int i = 0; i < streams.size(); i++) {
            temp.put(settingsManager.getBroadcastsPos().get(i), streams.get(i));
        }
        return temp;
    }
    public static void sendAvailableCommand(Player player) {
        ChatHelper.sendMessage(player, "&aAdvancedStreams &8| &fДоступные команды: ");
        ChatHelper.sendMessage(player, "&a▸ &fДобавление &cYouTube &fстрима - &a/stream YouTube [ссылка]");
        ChatHelper.sendMessage(player, "&a▸ &fДобавление &3VK &fстрима - &a/stream VK [ссылка]");
        ChatHelper.sendMessage(player, "&a▸ &fДобавление &dTwitch &fстрима - &a/stream Twitch [ссылка]");
        ChatHelper.sendMessage(player, "&a▸ &fСписок стримов - &a/streams");
        ChatHelper.sendMessage(player, "&a▸ &fУдаление стрима - &a/stream delete [Стример]");
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
    public static String getLink(Stream stream) {
        switch (stream.getPlatform()) {
            case YOUTUBE:
                return "https://youtu.be/" + stream.getId();
            case VK:
                return "https://vk.com/video" + stream.getOwnerId() + "_" + stream.getId();
            case TWITCH:
                return "https://twitch.tv/" + stream.getId();
            default:
                return null;
        }
    }
    public static String colored(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    public static List<String> colored(List<String> list) {
        List<String> out = new ArrayList<>();
        for(String line : list) {
            out.add(colored(line));
        }
        return out;
    }
    public static List<String> colored(String[] array) {
        List<String> out = new ArrayList<>();
        for(String line : array) {
            out.add(colored(line));
        }
        return out;
    }
    public static String stripColors(String msg) {
        String out = msg.replaceAll("[&][0-9a-fk-or]", "");
        out = out.replaceAll(String.valueOf((char) 194), "");
        return out.replaceAll("[\u00a7][0-9a-fk-or]", "");
    }
    public static ArrayList<Integer> convertListToArrayList(int[] num) {
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < num.length; i++) {
            nums.add(i,num[i]);
        }
        return nums;
    }
    public static boolean isNumber(String numberString) {
        try {
            Integer.parseInt(numberString);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    public static void cloneAndUnionList(HashMap<Integer, ItemStack> map, List<Integer> repeats, ItemStack obj) {
        for(int repeat : repeats) {
            map.put(repeat, obj);
        }
    }
    public static List<Integer> getPositionBroadcasts(String pos) {
        List<Integer> out = new ArrayList<>();
        String[] temp;
        if(pos.contains(","))
            temp = pos.replaceAll(" ","").split(",");
        else
            temp = new String[]{ pos };
        for (String line : temp) {
            if(line.contains("-")) {
                int start = Integer.parseInt(line.split("-")[0]);
                int end = Integer.parseInt(line.split("-")[1]);
                for(int i = start; i <= end; i++) {
                    out.add(i-1);
                }
            }
            else
                out.add(Integer.parseInt(line)-1);
        }
        return out;
    }
    public static String formatWithPlaceholder(String original, String placeholder, String replaced) {
        return colored(original.replaceAll(placeholder,replaced));
    }
    public static List<String> formatListWithPlaceholder(List<String> original, HashMap<String,String> placeholders) {
        List<String> out = new ArrayList<>();
        for(String line : original) {
            for(String placeholder : placeholders.keySet()) {
                if(line.contains(placeholder))
                    line = formatWithPlaceholder(line,placeholder,placeholders.get(placeholder));
            }
            out.add(line);
        }
        return out;
    }
    public static String formatStringWithPlaceholder(String line, HashMap<String,String> placeholders) {
        for(String placeholder : placeholders.keySet()) {
            if(line.contains(placeholder))
                line = formatWithPlaceholder(line,placeholder,placeholders.get(placeholder));
        }
        return line;
    }
    public static void debug(Exception e) {
        if(AdvancedStreams.getInstance().getSettingsManager() == null) return;
        if(AdvancedStreams.getInstance().getSettingsManager().isDebug())
            e.printStackTrace();
    }
    public static void debug(String str) {
        if(AdvancedStreams.getInstance().getSettingsManager() == null) return;
        if(AdvancedStreams.getInstance().getSettingsManager().isDebug())
            AdvancedStreams.getInstance().getLogger().warning(str);
    }
    public static CustomColor getColorFromString(String color) {
        try {
            return CustomColor.valueOf(color);
        }
        catch (Exception e) {
            return CustomColor.NONE;
        }
    }
    public static HashMap<String, String> getPlaceholders(Stream stream) {
        HashMap<String, String> out = new HashMap<>();
        out.put("%streamer%", stream.getYoutuber().getName());
        out.put("%duration%", stream.getFormatedDuration());
        out.put("%platform%", stream.getPlatform().toString());
        out.put("%viewers%", String.valueOf(stream.getViewers()));
        out.put("%title%", stream.getTitle());
        return out;
    }
    public static ArrayList<String> getContent(String curl) {
        ArrayList<String> content = new ArrayList<>();
        try {
            URL url = new URL(curl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String Line;
            while ((Line = in.readLine()) != null)
                content.add(Line);
            in.close();
        } catch (Exception ignored) {}
        return content;
    }
    public static ArrayList<String> getContent(String curl, String accessToken, String clientId) {
        ArrayList<String> content = new ArrayList<>();
        try {
            URL url = new URL(curl);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Authorization", "Bearer " + accessToken);
            http.setRequestProperty("Client-Id", clientId);

            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String Line;
            while ((Line = in.readLine()) != null)
                content.add(Line);
            http.disconnect();
        }
        catch (Exception ignored) {}
        return content;
    }
    public static String arrayListToString(ArrayList<String> list) {
        StringBuilder content = new StringBuilder();
        for (String s : list)
            content.append(s);
        return content.toString();
    }
    public static String getYouTubeVideoID(String url) {
        if(!url.contains("youtu")) return null;
        if(url.contains("youtu.be")) {
            return url.substring(url.lastIndexOf('/')+1);
        }
        else {
            return url.substring(url.lastIndexOf("?v")+3);
        }
    }
    public static String[] getVKIds(String url) {
        if(!url.contains("vk.com")) return null;
        if(url.contains("%2F")) {
            url = url.substring(url.lastIndexOf("video")+5, url.indexOf("%2F"));
        }
        else {
            url = url.substring(url.lastIndexOf("video")+5);
        }
        return url.split("_");
    }
    public static String formatTitle(String title, SettingsManager manager) {
        if (title.length() > manager.getLengthTitle()) return title.substring(0, manager.getLengthTitle()) + "...";
        else return title;
    }
}
