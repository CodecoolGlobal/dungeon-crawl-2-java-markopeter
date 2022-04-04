package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.Map;

public class Ghost extends Actor{
    private int health = 4;
    private int damage = 2;
    private int ghostMoveCounter = 0;
    public Ghost(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    @Override
    public void checkForCollision(int dx, int dy) {
        Cell nextCell = super.getCell().getNeighbor(dx, dy);
        if(!(nextCell.getType() == CellType.WALL)){
            if(nextCell.getActor() == null){
                move(nextCell);
            }
        }
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int difference) {

    }

    @Override
    public int getDamage() {
        return damage;
    }

    public int[] ghostMove(){

        int [] ghostCoordinates = {0,0};
        if(ghostMoveCounter == 0 || ghostMoveCounter == 1){
            ghostCoordinates[0]--;
            ghostMoveCounter++;
        }
        else if(ghostMoveCounter == 2 || ghostMoveCounter == 3){
            ghostCoordinates[1]--;
            ghostMoveCounter++;
        }
        else if(ghostMoveCounter == 4 || ghostMoveCounter == 5){
            ghostCoordinates[0]++;
            ghostMoveCounter++;
        }
        else if(ghostMoveCounter == 6 || ghostMoveCounter == 7){
            ghostCoordinates[1]++;
            ghostMoveCounter++;
            if(ghostMoveCounter > 7){
                ghostMoveCounter =0;
            }
        }
        return ghostCoordinates;
    }
}
