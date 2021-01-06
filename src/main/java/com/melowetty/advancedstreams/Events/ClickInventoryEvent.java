package com.melowetty.advancedstreams.Events;

import com.melowetty.advancedstreams.AdvancedStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickInventoryEvent implements Listener {
    @EventHandler
    public void onInventoryClick (InventoryClickEvent e) {
        if(e.getClickedInventory() == null) return;
        AdvancedStreams plugin = AdvancedStreams.getInstance();
        if (e.getClickedInventory().getName().equalsIgnoreCase(plugin.getNameMenu())) {
            Player clicked = (Player)e.getWhoClicked();
            e.setCancelled(true);
        }
    }
}
