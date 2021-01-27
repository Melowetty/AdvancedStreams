package com.melowetty.advancedstreams.events;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.utils.ChatHelper;
import com.melowetty.advancedstreams.utils.Helper;
import net.md_5.bungee.api.chat.TextComponent;
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
            if(e.getCurrentItem().getType() == plugin.getSettingsManager().getStreamMaterial()) {
                String link = Helper.getLink(plugin.getStreamsManager().getStreamWithSlot(e.getSlot()));
                TextComponent component = ChatHelper.generateTextComponent("Ссылка на стрим", "Перейдите по ссылке", link);
                ChatHelper.sendTextComponent(clicked, component);
                clicked.closeInventory();
            }
            e.setCancelled(true);
        }
    }
}
