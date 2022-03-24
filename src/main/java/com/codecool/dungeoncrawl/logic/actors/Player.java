package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player extends Actor {

    private HashMap<Items, Integer> itemList = new HashMap<>();

    private Cell cell;
    private int health = 10;
    private int damage = 1;
    private boolean onPortal = false;

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
            addToInventory(itemList, item);
            if(item instanceof Swords){
                ((Swords) item).increaseDamage(damage, ((Swords) item).getDamage());
            }
        }

    }

    @Override
    public void checkForCollision(int dx, int dy){
        Cell nextCell = super.getCell().getNeighbor(dx, dy);

        if(nextCell.getType() == CellType.DOOR){
            if(hasThatKey(CellType.DOOR.getItemFunction())){
                move(nextCell);
            }
        }else if(nextCell.getType() == CellType.PORTAL){
            onPortal = true;
        }
        else if(!(nextCell.getType() == CellType.WALL)){
            if(nextCell.getActor() == null){
                move(nextCell);
            }else{
                fight(super.getCell().getActor(), nextCell.getActor());
                checkForCollision(-dx,-dy);
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

    public boolean hasThatKey(String keyName){
        Set<Items> items = itemList.keySet();
        for(Items item: items){
            if(item.getName().equals(keyName)){
                return true;
            }
        }
        return false;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public String getTileName() {
        return "player";
    }

    public HashMap<Items, Integer> getItemList(){
        return itemList;
    }

    public boolean isOnPortal() {
        return onPortal;
    }

    public void setOnPortal(boolean onPortal) {
        this.onPortal = onPortal;
    }


}
