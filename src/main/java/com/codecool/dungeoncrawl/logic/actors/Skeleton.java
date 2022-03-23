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
        int [] randomCoordinates = new int[2];
        int direction = nextInt(4);
        switch (direction) {
            case 0:
                randomCoordinates[0] = 0;
                randomCoordinates[1] = 1;
                break;
            case 1:
                randomCoordinates[0] = 0;
                randomCoordinates[1] = -1;
                break;
            case 2:
                randomCoordinates[0] = -1;
                randomCoordinates[1] = 0;
                break;
            case 3:
                randomCoordinates[0] = 1;
                randomCoordinates[1] = 0;
                break;
        }

        return randomCoordinates;
    }

    public int nextInt(int upper) {
            return RANDOM.nextInt(upper);
        }


}
