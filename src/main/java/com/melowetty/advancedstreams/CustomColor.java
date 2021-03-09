package com.melowetty.advancedstreams;

import org.bukkit.Color;
import org.bukkit.DyeColor;

public enum CustomColor {
    GREEN(Color.fromRGB(85, 255, 85), DyeColor.LIME),
    RED(Color.fromRGB(255, 85, 85), DyeColor.RED),
    BLUE(Color.fromRGB(85, 85, 255), DyeColor.LIGHT_BLUE),
    YELLOW(Color.fromRGB(255, 255, 85), DyeColor.YELLOW),
    AQUA(Color.fromRGB(85, 255, 255), DyeColor.CYAN),
    BLACK(Color.BLACK, DyeColor.BLACK),
    GOLD(Color.fromRGB(255, 170, 0), DyeColor.ORANGE),
    DARK_BLUE(Color.fromRGB(0, 0, 170), DyeColor.BLUE),
    DARK_GREEN(Color.fromRGB(0, 170, 0), DyeColor.GREEN),
    DARK_RED(Color.fromRGB(170, 0, 0), DyeColor.BROWN),
    DARK_PURPLE(Color.fromRGB(170, 0, 170), DyeColor.MAGENTA),
    GRAY(Color.fromRGB(170, 170, 170), DyeColor.SILVER),
    DARK_GRAY(Color.fromRGB(85, 85, 85), DyeColor.GRAY),
    LIGHT_PURPLE(Color.fromRGB(255, 85, 255), DyeColor.PINK),
    WHITE(Color.WHITE, DyeColor.WHITE),
    NONE(null, null);

    private final Color color;
    private final DyeColor dyeColor;

    CustomColor(Color color, DyeColor dye) {
        this.color = color;
        this.dyeColor = dye;
    }


    public Color getColor() {
        return this.color;
    }

    public DyeColor getDyeColor() {
        return this.dyeColor;
    }
}
