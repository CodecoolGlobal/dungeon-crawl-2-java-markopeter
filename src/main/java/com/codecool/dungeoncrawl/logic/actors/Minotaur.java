package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Minotaur extends Actor{

    private int health = 4;
    private int damage = 2;
    public Minotaur(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "minotaur";
    }

    @Override
    public void checkForCollision(int dx, int dy) {
        Cell nextCell = super.getCell().getNeighbor(dx, dy);
        move(nextCell);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int difference) {
        health += difference;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public int[] minotaurMove(int x, int y){
        int [] minotaurCoordinates = {0,0};
        int minotaurX = getX();
        int minotaurY = getY();
        if(x > minotaurX && y == minotaurY){
            minotaurCoordinates[0]--;
        }
        else if(x <  minotaurX && y == minotaurY){
            minotaurCoordinates[0]++;
        }
        else if(x == minotaurX && y > minotaurY){
            minotaurCoordinates[1]++;
        }
        else if(x == minotaurX && y < minotaurY){
            minotaurCoordinates[1]--;
        }
        else if(x > minotaurX && y > minotaurY){
            minotaurCoordinates[0]++;
            minotaurCoordinates[1]++;
        }
        else if(x < minotaurX && y < minotaurY){
            minotaurCoordinates[0]--;
            minotaurCoordinates[1]--;
        }
        else if(x < minotaurX && y > minotaurY){
            minotaurCoordinates[0]--;
            minotaurCoordinates[1]++;
        }
        else if(x > minotaurX && y < minotaurY){
            minotaurCoordinates[0]++;
            minotaurCoordinates[1]--;
        }
        return minotaurCoordinates;
    }
}
