package com.melowetty.advancedstreams.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null || event.getClickedInventory().getHolder() == null) {
            return;
        }
        if (inventory.getHolder() instanceof StreamsInventory) {
            ((StreamsInventory) inventory.getHolder()).onInventoryClick(event);
        }
    }
}
