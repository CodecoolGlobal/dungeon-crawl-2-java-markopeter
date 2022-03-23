package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Items{

    public Key(Cell cell) {
        super("key", cell);
    }

    @Override
    public String getTileName() {
        return "key";
    }
}
