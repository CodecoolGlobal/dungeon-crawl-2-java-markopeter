package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.HashMap;
import java.util.Set;

import static com.codecool.dungeoncrawl.model.SoundAndMusic.playSound;


public class Player extends Actor {
    public String fightSound = "/66352853.wav";
    private HashMap<Items, Integer> itemList = new HashMap<>();
    private int health = 10;
    private int damage = 3;
    private boolean onPortal = false;
    private String name = "Unknown";
    public Player(Cell cell) {

        super(cell);
    }

    public void addToInventory(Items item){

            if(itemList.containsKey(item)){
                itemList.replace(item,itemList.get(item) + 1);
            } else{
                itemList.put(item, 1);
            }
    }

    public void pickUpItem(){
        Items item = getCell().getItem();
        if(item != null){
            addToInventory(item);
            if(item instanceof Swords){
                setDamage(damage+item.getDamage());
            }
        }

    }

    @Override
    public void checkForCollision(int dx, int dy){
        Cell nextCell = super.getCell().getNeighbor(0,0);
        boolean isOffMap = false;
        try{
            nextCell = super.getCell().getNeighbor(dx, dy);
        }catch (ArrayIndexOutOfBoundsException e){
            isOffMap = true;
        }


        if(!isOffMap){
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
                    playSound(fightSound);
                    fight(super.getCell().getActor(), nextCell.getActor());
                    checkForCollision(-dx,-dy);
                }
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
        if(isAlive()){
            return "player";
        }else{
            return "deadplayer";
        }

    }

    public HashMap<Items, Integer> getItemList(){
        return itemList;
    }

    public void setItemList(HashMap<Items, Integer> itemList){
        this.itemList = itemList;
    }

    public boolean isOnPortal() {
        return onPortal;
    }

    public void setOnPortal(boolean onPortal) {
        this.onPortal = onPortal;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
