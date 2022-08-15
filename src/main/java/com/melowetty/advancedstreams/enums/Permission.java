package com.melowetty.advancedstreams.enums;

public enum Permission {
    STREAM_LIST("advancedstreams.stream.list"),
    STREAM_MANAGE("advancedstreams.stream.manage"),
    STREAM_ADD_TWITCH("advancedstreams.stream.add.twitch"),
    STREAM_ADD_VK("advancedstreams,stream.add.vk"),
    STREAM_ADD_YOUTUBE("advancedstreams.stream.add.youtube"),
    STREAM_DELETE("advancedstreams.stream.delete");
    private final String bukkitPermission;
    Permission(String bukkitPermission) {
        this.bukkitPermission = bukkitPermission;
    }

    public String getBukkitPermission() {
        return bukkitPermission;
    }
}
