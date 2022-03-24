package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Swords extends Items{

    public Swords(Cell cell){
        super("sword", cell);
    }

    public String getTileName() {
        return "swords";
    }

    @Override
    public int getDamage() {
        int DAMAGE = 10;
        return DAMAGE;
    }
}
