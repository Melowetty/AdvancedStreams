package com.melowetty.advancedstreams.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatHelper {
    public static void sendMessage(Player player, String... strings) {
        if (player == null) return;
        for(String str : strings) {
            player.sendMessage(Helper.colored(str));
        }
    }

    public static void sendMessage(Player player, List<String> strings) {
        if (player == null) return;
        for(String str : strings) {
            player.sendMessage(Helper.colored(str));
        }
    }

    public static void sendMessage(Player player, String str) {
        if (player == null) return;
        player.sendMessage(Helper.colored(str));
    }

    public static TextComponent generateTextComponent(String context, String hover, String url) {
        TextComponent tc = new TextComponent();
        tc.setText(Helper.colored(context));
        if (hover != null && !hover.equals(""))
            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(Helper.colored(hover))).create()));
        if (url != null && !url.equals(""))
            tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return tc;
    }

    public static void sendTextComponent(Player player, TextComponent textComponent) {
        if (player == null) return;
        player.spigot().sendMessage(textComponent);
    }
}
