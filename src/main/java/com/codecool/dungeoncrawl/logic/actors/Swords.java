package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Swords extends Items{

    private int damage = 10;
    public Swords(Cell cell){
        super("sword", cell);
    }

    public String getTileName() {
        return "swords";
    }

    public void increaseDamage(int damage, int newDamage){
        damage = newDamage;
    }
    public int getDamage() {
        return damage;
    }
}
