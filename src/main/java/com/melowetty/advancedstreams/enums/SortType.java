package com.melowetty.advancedstreams.enums;

public enum SortType {
    BY_DURATION("По продолжительности"),
    BY_VIEWERS("По количеству зрителей"),
    DEFAULT("По мере добавления");
    private final String name;
    SortType(String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
