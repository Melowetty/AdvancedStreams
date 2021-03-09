package com.melowetty.advancedstreams.utils;

import com.melowetty.advancedstreams.ResponseStatus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
    public static Builder builder(Inventory inv) { return new Builder(inv); }
    public static Builder builder(String title) { return new Builder(title); }
    public static Builder builder(String title, int size) { return new Builder(title, size); }
    public static Builder builder(Player owner) { return new Builder(owner); }
    public static Builder builder(Player owner, String  title) { return new Builder(owner, title); }
    public static Builder builder(Player owner, int size, String  title) { return new Builder(owner, size, title); }
    public static class Builder {
        private Inventory inventory;

        public Builder(Inventory inv) {
            inventory = inv;
        }

        public Builder(String title) {
            inventory = Bukkit.createInventory(null, 54, title);
        }

        public Builder(String title, int size) {
            inventory = Bukkit.createInventory(null, size, title);
        }

        public Builder(Player owner) {
            inventory = Bukkit.createInventory(owner, 27);
        }

        public Builder(Player owner, String title) {
            inventory = Bukkit.createInventory(owner, 27, title);
        }

        public Builder(Player owner, int size, String title) {
            inventory = Bukkit.createInventory(owner, size, title);
        }

        public Builder withItem(ItemStack item) {
            inventory.addItem(item);
            return this;
        }

        public Builder withItem(int pos, ItemStack item) {
            inventory.setItem(pos, item);
            return this;
        }

        public Builder withItems(List<ItemStack> items) {
            fill(inventory, items);
            return this;
        }

        public Builder withItems(HashMap<Integer, ItemStack> items) {
            fill(inventory, items);
            return this;
        }

        public Inventory build() {
            return inventory;
        }
    }
}
