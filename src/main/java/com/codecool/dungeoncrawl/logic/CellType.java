package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty", null,false),
    FLOOR("floor",null,false),
    WALL("wall",null,false),
    DOOR("door","key", false),
    BUTTON("button", null, false);


    private final String tileName;
    private final String itemFunction;
    private boolean steppedOn;

    CellType(String tileName, String itemFunction, boolean steppedOn) {
        this.tileName = tileName;
        this.itemFunction = itemFunction;
        this.steppedOn = steppedOn;

    }

    public String getItemFunction(){
        return itemFunction;
    }

    public String getTileName() {
        return tileName;
    }

    public void setSteppedOn(boolean steppedOn) {
        this.steppedOn = steppedOn;
    }
}
