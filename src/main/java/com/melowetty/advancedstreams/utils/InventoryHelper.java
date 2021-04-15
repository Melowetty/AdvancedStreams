package com.melowetty.advancedstreams.utils;

import com.melowetty.advancedstreams.enums.ResponseStatus;
import com.melowetty.advancedstreams.gui.StreamsInventory;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryHelper {
    public static void fill(Inventory inventory, Material material, boolean reverse, int[] temp) {
        if(temp==null) return;
        ArrayList<Integer> nums = Helper.convertListToArrayList(temp);
        for(int i = 0; i < inventory.getSize(); i++) {
            if(reverse) {
                if(!nums.contains(i)) continue;
            }
            else {
                if(nums.contains(i)) continue;
            }
            inventory.setItem(i,new ItemStack(material));
        }
    }

    public static ResponseStatus fill(Inventory inv, HashMap<Integer,ItemStack> items) {
        try {
            for(Integer pos : items.keySet()) {
                inv.setItem(pos,items.get(pos));
            }
            return ResponseStatus.SUCCESS;
        }
        catch (Exception e) {
            Helper.debug(e);
            return ResponseStatus.NULL;
        }
    }

    public static ResponseStatus fill(Inventory inv, List<ItemStack> items) {
        try {
            for(ItemStack item : items) {
                inv.addItem(item);
            }
            return ResponseStatus.SUCCESS;
        }
        catch (Exception e) {
            Helper.debug(e);
            return ResponseStatus.NULL;
        }
    }

    public static Builder builder(int size, String title) { return new Builder(size, title); }

    public static class Builder {
        private final StreamsInventory inventory;

        public Builder(int rows, String title) {

            inventory = new StreamsInventory(rows, title);
        }

        public Builder withItem(ItemStack item) {
            inventory.getInventory().addItem(item);
            return this;
        }

        public Builder withItem(int pos, ItemStack item) {
            inventory.getInventory().setItem(pos, item);
            return this;
        }

        public Builder withItems(List<ItemStack> items) {
            fill(inventory.getInventory(), items);
            return this;
        }

        public Builder withItems(HashMap<Integer, ItemStack> items) {
            fill(inventory.getInventory(), items);
            return this;
        }

        public StreamsInventory build() {
            return inventory;
        }
    }
}
