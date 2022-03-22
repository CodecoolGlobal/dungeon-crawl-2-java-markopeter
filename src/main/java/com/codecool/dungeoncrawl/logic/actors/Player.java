package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.LinkedHashMap;
import java.util.Map;

public class Player extends Actor {

    private Map<Items, Integer> itemList = new LinkedHashMap<Items, Integer>();

    public Player(Cell cell) {
        super(cell);
    }

    public <T extends Items> void addToInventory(Map<Items, Integer> itemList, Items item){
            T key = (T) item;
            if(itemList.containsKey(key)){
                itemList.replace(key,itemList.get(key) + 1);
            } else{
                itemList.put(key, 1);
            }
    }

    public void pickUpItem(){
        Items item = getCell().getItem();
        if(item != null){
            if(item instanceof Swords){
                addToInventory(itemList, item);
            }
        }

    }

    public String getTileName() {
        return "player";
    }
}
