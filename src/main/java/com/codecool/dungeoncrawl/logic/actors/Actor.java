package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {


    private Cell cell;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

     public void move(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }



    public abstract void checkForCollision(int dx, int dy);

    public void fight(Actor attacker, Actor attacked){
        attacked.takeDamage(attacker.getDamage());
        if(attacked.isAlive()){
            attacker.takeDamage(attacked.getDamage());
        }else{
            attacked.getCell().setActor(null);
        }
    }

    public void takeDamage(int damageAmount){
        setHealth(-damageAmount);
    }

    public boolean isAlive(){
        return getHealth() > 0;
    }

    public abstract int getHealth();

    public abstract void setHealth(int difference);

    public abstract int getDamage();

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
