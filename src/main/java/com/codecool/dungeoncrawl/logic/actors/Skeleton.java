package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Random;

public class Skeleton extends Actor {
    private final Random RANDOM = new Random();
    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public void move(int x, int y){
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

        getCell().setActor(null);
        nextCell.setActor(this);
        setCell(nextCell);
    }

    public int nextInt(int upper) {
            return RANDOM.nextInt(upper);
        }


}
