package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Random;

public class Skeleton extends Actor {

    private final Random RANDOM = new Random();
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

    public int[] movement(){
        Cell nextCell;
        nextCell = getCell();
        int direction = nextInt(4);
        switch (direction) {
            case 0:
                nextCell = getCell().getNeighbor(0, 1);
                break;
            case 1:
                nextCell = getCell().getNeighbor(0, -1);
                break;
            case 2:
                nextCell = getCell().getNeighbor(-1, 0);
                break;
            case 3:
                nextCell = getCell().getNeighbor(1, 0);
                break;
        }

        int x = nextCell.getX();
        int y = nextCell.getY();
        int [] randomCoordinates = {x,y};
        return randomCoordinates;
    }

    public int nextInt(int upper) {
            return RANDOM.nextInt(upper);
        }


}
