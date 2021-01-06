package com.melowetty.advancedstreams.Utils;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.CustomColor;
import com.melowetty.advancedstreams.Stream;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Helper {
    public static String formatDuration(Long duration) {
        Long real_duration = Math.abs(System.currentTimeMillis() - duration);
        SimpleDateFormat format = new SimpleDateFormat("HHч. mmмин.");
        return format.format(real_duration);
    }
    public static Long youtubeDataToLong(String date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return f.parse(date).getTime();
        } catch (ParseException e) {
            debug(e);
        }
        return null;
    }
    public static String colored(String str) {
        return ChatColor.translateAlternateColorCodes('&',str);
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
            Helper.debug(ex);
            return false;
        }
    }
    public static TextComponent generateTextComponentWithURL(String context, String hover, String url) {
        TextComponent tc = new TextComponent();
        tc.setText(colored(context));
        if (hover != null && !hover.equals(""))
            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(colored(hover))).create()));
        if (url != null && !url.equals(""))
            tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return tc;
    }
    public static void sendTextComponent(Player player, TextComponent textComponent) {
        player.spigot().sendMessage(textComponent);
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
    public static CustomColor getColorFromString(String color) {
        try {
            return CustomColor.valueOf(color);
        }
        catch (Exception e) {
            debug(e);
            return CustomColor.NONE;
        }
    }
    public static HashMap<String, String> getPlaceholders(Stream stream) {
        HashMap<String, String> out = new HashMap<>();
        out.put("%streamer%", stream.getYouTuber().getName());
        out.put("%duration%", stream.getFormatedDuration());
        out.put("%platform%", stream.getPlatform().toString());
        out.put("%viewers%", String.valueOf(stream.getViewers()));
        out.put("%title%", stream.getTitle());
        return out;
    }
}
