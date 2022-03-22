package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.LinkedHashMap;
import java.util.Map;

public class Player extends Actor {

    private Map<Items, Integer> items = new LinkedHashMap<Items, Integer>();

    public Player(Cell cell) {
        super(cell);
    }



    public String getTileName() {
        return "player";
    }
}
