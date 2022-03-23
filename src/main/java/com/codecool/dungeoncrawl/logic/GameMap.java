package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private Player player;
    private Actor actor;
    List<Actor> enemies = new ArrayList<>();

    public GameMap(int width, int height, CellType defaultCellType) {

        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Actor> getEnemies() {
        return enemies;
    }

    public void addEnemy(Actor enemy) {
        enemies.add(enemy);
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public void moveEnemy() {

        for (Actor enemy: enemies) {
            if(enemy.isAlive()){
                if(enemy instanceof Skeleton){
                    int [] coord = ((Skeleton) enemy).movement();
                    enemy.checkForCollision(coord[0], coord[1]);
                }
                if(enemy instanceof Ghost){
                    int [] coord = ((Ghost) enemy).ghostMove();
                    enemy.checkForCollision(coord[0], coord[1]);
                }
            }
        }
    }

}

