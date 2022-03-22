package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public abstract class Items {
    private String name;
    private Cell cell;
    private int x;
    private int y;

    public Items(String name, Cell cell){
        this.cell = cell;
        this.name = name;
        this.y = cell.getY();
        this.x = cell.getX();
        this.cell.setItem(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void emptyCell(){
        cell.setItem(null);
    }

}
