package com.melowetty.advancedstreams.utils;

import com.melowetty.advancedstreams.CustomColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Method;
import java.util.*;

public class ItemHelper {
    public static boolean isColorable(ItemStack itemstack) {
        return (itemstack.getType().equals(Material.STAINED_CLAY)
                || itemstack.getType().equals(Material.WOOL)
                || itemstack.getType().equals(Material.CARPET)
                || itemstack.getType().equals(Material.STAINED_GLASS)
                || itemstack.getType().equals(Material.STAINED_GLASS_PANE)
                || itemstack.getType().equals(Material.CONCRETE)
                || itemstack.getType().equals(Material.BED));
    }
    public static Material parseMaterial(String material) {
        try {
            if (Helper.isNumber(material)) {
                return Material.getMaterial(Integer.parseInt(material));
            } else {
                return Material.getMaterial(material.toUpperCase());
            }
        } catch (Exception ex) {
            Helper.debug(ex);
        }

        return null;
    }
    public static void applyColor(ItemStack item, ItemMeta meta, CustomColor color) {
        if (isColorable(item)) {
            item.setDurability(color.getDyeColor().getWoolData());
        } else {
            Material type = item.getType();
            if (type == Material.TIPPED_ARROW || type == Material.POTION
                    || type == Material.SPLASH_POTION || type == Material.LINGERING_POTION) return;

            Method colorableMethod = getColorableMethod(item.getType());
            if (colorableMethod != null) {
                colorableMethod.setAccessible(true);
                try {
                    colorableMethod.invoke(meta, color.getColor());
                } catch (Exception e) {
                    Helper.debug(e);
                }
            }
        }
    }
    public static Method getColorableMethod(Material mat) {
        try {
            ItemStack tempStack = new ItemStack(mat, 1);
            Method method =
                    tempStack.getItemMeta().getClass().getMethod("setColor", Color.class);
            if (method != null) {
                return method;
            }
        } catch (Exception ignored) {
        }

        return null;
    }
    public static ItemStack createItem(Material type) {
        return createItem(type, 1);
    }

    public static ItemStack createItem(Material type, int amount) {
        return builder(type, amount).build();
    }

    public static ItemStack createItem(Material type, int amount, int damage) {
        return builder(type, amount, damage).build();
    }

    public static ItemStack createItem(Material type, String name, String ... lore) {
        return builder(type, 1)
                .withLore(Arrays.asList(lore))
                .withName(name)
                .build();
    }

    public static ItemStack createItem(Material type, int amount, String name, String ... lore) {
        return builder(type, amount)
                .withLore(Arrays.asList(lore))
                .withName(name)
                .build();
    }

    public static ItemStack createItem(Material type, int amount, int damage, String name, String ... lore) {
        return builder(type, amount)
                .withDamage(damage)
                .withLore(Arrays.asList(lore))
                .withName(name)
                .build();
    }

    public static Builder builder(Material type) {
        return builder(type, 1);
    }

    public static Builder builder(Material type, int amount) {
        return new Builder(type, amount);
    }

    public static Builder builder(Material type, int amount, int damage) {
        return new Builder(type, amount, damage);
    }

    public static Builder builder(ItemStack itemStack) {
        return new Builder(itemStack);
    }

    public static class Builder {
        private ItemStack item;
        private ItemMeta meta;

        public Builder(Material type, int amount) {
            this.item = new ItemStack(type, amount);
            this.meta = this.item.getItemMeta();
        }

        public Builder(Material type, int amount, int damage) {
            this.item = new ItemStack(type, amount, (short) damage);
            this.meta = this.item.getItemMeta();
        }

        public Builder(ItemStack itemStack) {
            this.item = itemStack.clone();
            this.meta = this.item.getItemMeta();
        }

        public Builder withColor(CustomColor color) {
            applyColor(item, meta, color);
            return this;
        }

        public Builder withDamage(int damage) {
            item.setDurability((short) damage);
            return this;
        }

        public Builder withName(String name) {
            meta.setDisplayName(name);
            return this;
        }

        public Builder withLore(String ... lore) {
            meta.setLore(Arrays.asList(lore));
            return this;
        }

        public Builder withLore(Collection<? extends String> lore) {
            meta.setLore(new ArrayList<>(lore));
            return this;
        }

        public Builder addLore(String ... loreLines) {
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            Collections.addAll(lore, loreLines);
            meta.setLore(lore);
            return this;
        }

        public Builder addEnchant(Enchantment enchantment, int level) {
            meta.addEnchant(enchantment, level, true);
            return this;
        }

        public Builder addFlags(ItemFlag... itemFlags) {
            meta.addItemFlags(itemFlags);
            return this;
        }

        public ItemStack build() {
            item.setItemMeta(meta);
            return item;
        }
    }
}
