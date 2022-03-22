package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.LinkedHashMap;
import java.util.Map;

public class Player extends Actor {

    private Map<Items, Integer> itemList = new LinkedHashMap<>();

    private Cell cell;
    private int health = 10;
    private int damage = 5;

    public Player(Cell cell) {

        super(cell);
    }

    public void addToInventory(Map<Items, Integer> itemList, Items item){

            if(itemList.containsKey(item)){
                itemList.replace(item,itemList.get(item) + 1);
            } else{
                itemList.put(item, 1);
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

    public void setDamage(int damage){
        this.damage = damage;
    }

    public String getTileName() {
        return "player";
    }
    @Override
    public void move(int dx, int dy) {
        Cell nextCell = getCell().getNeighbor(dx, dy);
        if (nextCell.getActor() == null) {
            getCell().setActor(null);
            nextCell.setActor(this);
            setCell(nextCell);
    }
    }
}
