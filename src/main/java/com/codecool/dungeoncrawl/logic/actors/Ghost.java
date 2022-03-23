package com.codecool.dungeoncrawl.logic.actors;

public class Ghost extends Actor{

    @Override
    public String getTileName() {
        return "ghost";
    }

    @Override
    public void checkForCollision(int dx, int dy) {

    }

    @Override
    public int getHealth() {
        return 0;
    }

    @Override
    public void setHealth(int difference) {

    }

    @Override
    public int getDamage() {
        return 0;
    }

    public int[] ghostMove(int moveCounter){
        int [] ghostCoordinates = new int[2];
        if(moveCounter == 0 || moveCounter == 1){
            ghostCoordinates[0] = -1;
            ghostCoordinates[1] = 0;
        }
        else if(moveCounter == 2 || moveCounter == 3){
            ghostCoordinates[0] = 0;
            ghostCoordinates[1] = -1;
        }
        else if(moveCounter == 4 || moveCounter == 5){
            ghostCoordinates[0] = +1;
            ghostCoordinates[1] = 0;
        }
        else if(moveCounter == 6 || moveCounter == 7){
            ghostCoordinates[0] = 0;
            ghostCoordinates[1] = +1;
            if(moveCounter == 7){
                moveCounter =0;
            }
        }
        return ghostCoordinates;
    }
}
