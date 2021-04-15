package com.melowetty.advancedstreams.gui;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.utils.ChatHelper;
import com.melowetty.advancedstreams.utils.Helper;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class StreamsInventory implements InventoryHolder {
    private final Inventory inventory;

    public StreamsInventory(int rows, String title) {
        this.inventory = Bukkit.createInventory(this, rows * 9, title);
    }
    @Override
    public Inventory getInventory() {
        return inventory;
    }
    public void onInventoryClick(InventoryClickEvent event) {
        AdvancedStreams plugin = AdvancedStreams.getInstance();
        Player clicked = (Player) event.getWhoClicked();
        if(event.getCurrentItem().getType() == plugin.getSettingsManager().getStreamMaterial()) {
            String link = Helper.getLink(plugin.getStreamsManager().getStreamWithSlot(event.getSlot()));
            TextComponent component = ChatHelper.generateTextComponent("Ссылка на стрим", "Перейдите по ссылке", link);
            ChatHelper.sendTextComponent(clicked, component);
            clicked.closeInventory();
        }
        event.setCancelled(true);
    }
}
