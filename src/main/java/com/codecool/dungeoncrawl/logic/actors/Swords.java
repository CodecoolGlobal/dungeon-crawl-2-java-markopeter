package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Swords extends Items{

    private final int DAMAGE = 10;
    public Swords(Cell cell){
        super("sword", cell);
    }

    public String getTileName() {
        return "swords";
    }

    @Override
    public int getDamage() {
        return DAMAGE;
    }
}
