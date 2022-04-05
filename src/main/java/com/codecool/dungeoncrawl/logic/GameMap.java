package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.codecool.dungeoncrawl.logic.CellType.*;

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

        for (Actor enemy : enemies) {
            if (enemy.isAlive()) {
                if (enemy instanceof Skeleton) {
                    int[] coord = ((Skeleton) enemy).movement();
                    enemy.checkForCollision(coord[0], coord[1]);
                }
                if (enemy instanceof Ghost) {
                    int[] coord = ((Ghost) enemy).ghostMove();
                    enemy.checkForCollision(coord[0], coord[1]);
                }
                if (enemy instanceof Minotaur) {
                    int[] coord = ((Minotaur) enemy).minotaurMove(player.getX(), player.getY());
                    enemy.checkForCollision(coord[0], coord[1]);
                }


            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }


    public String convertGameMapToString() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < width; x++) {
            sb.append("\n");
            for (int y = 0; y < height; y++) {

                if (cells[x][y].getType() == FLOOR && Objects.equals(cells[x][y].getTileName(), "sword")) {
                    sb.append("w");
                } else if (cells[x][y].getType() == FLOOR && Objects.equals(cells[x][y].getTileName(), "key")) {
                    sb.append("k");
                } else if (cells[x][y].getType() == FLOOR) {
                    sb.append(".");
                } else if (cells[x][y].getType() == FLOOR && Objects.equals(cells[x][y].getActor().getTileName(), "ghost")) {
                    sb.append("g");
                } else if (cells[x][y].getType() == FLOOR && Objects.equals(cells[x][y].getActor().getTileName(), "skeleton")) {
                    sb.append("s");
                } else if (cells[x][y].getType() == FLOOR && Objects.equals(cells[x][y].getActor().getTileName(), "minotaur")) {
                    sb.append("m");
                } else if (cells[x][y].getType() == BUTTON) {
                    sb.append("b");
                } else if (cells[x][y].getType() == FLOOR && Objects.equals(cells[x][y].getTileName(), "player")) {
                    sb.append("@");
                } else if (cells[x][y].getType() == PORTAL) {
                    sb.append("p");
                } else if (cells[x][y].getType() == DOOR) {
                    sb.append("d");
                } else if (cells[x][y].getType() == EMPTY) {
                    sb.append(" ");
                }
            }
        }
        String table = sb.toString();
        return table;

    }
}

