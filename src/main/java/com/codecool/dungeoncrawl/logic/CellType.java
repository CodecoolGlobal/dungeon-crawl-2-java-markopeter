package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty", null),
    FLOOR("floor",null),
    WALL("wall",null),
    DOOR("door","brownkey");

    private final String tileName;
    private final String itemFunction;

    CellType(String tileName, String itemFunction) {
        this.tileName = tileName;
        this.itemFunction = itemFunction;

    }

    public String getTileName() {
        return tileName;
    }
}
