package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Items{

    private final int DAMAGE = 0;

    public Key(Cell cell) {
        super("key", cell);
    }

    @Override
    public String getTileName() {
        return "key";
    }

    @Override
    public int getDamage(){
        return DAMAGE;
    }
}
