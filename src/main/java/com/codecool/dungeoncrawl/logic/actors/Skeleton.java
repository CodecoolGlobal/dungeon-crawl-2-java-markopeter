package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

// a move-ot akarjuk overrideolni ha azt akarjuk mozogni

public class Skeleton extends Actor {

    private int health = 4;
    private int damage = 2;

    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public int getHealth(){
        return health;
    }

    @Override
    public void setHealth(int difference){
        health += difference;
    }

    @Override
    public int getDamage(){
        return damage;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
