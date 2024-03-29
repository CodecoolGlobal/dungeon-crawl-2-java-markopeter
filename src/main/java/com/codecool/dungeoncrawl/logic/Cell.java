package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Items;



public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Items item;
    private GameMap gameMap;
    private int x, y;

    public Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public Cell getNeighbor(int dx, int dy) {
        if(x+dx >= gameMap.getWidth() || y+dy >= gameMap.getHeight() || y+dy < 0 || x+dx < 0){
            return null;
        }else{
            return gameMap.getCell(x + dx, y + dy);
        }

    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setItem(Items item) { this.item = item; }

    public Items getItem() {
        return item;
    }

}
