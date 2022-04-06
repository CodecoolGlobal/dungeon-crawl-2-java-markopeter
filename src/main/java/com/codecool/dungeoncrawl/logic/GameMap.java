package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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
        sb.append(width).append(" ").append(height);

        for (int y = 0; y < height; y++) {
            sb.append("\n");
            for (int x = 0; x < width; x++) {

                if (cells[x][y].getType() == FLOOR) {
                    if (cells[x][y].getItem() != null) {
                        if (Objects.equals(cells[x][y].getItem().getTileName(), "swords")) {
                            sb.append("w");
                        } else if (Objects.equals(cells[x][y].getItem().getTileName(), "key")) {
                            sb.append("k");
                        }
                    } else if (cells[x][y].getActor() != null) {
                        if (Objects.equals(cells[x][y].getActor().getTileName(), "skeleton")) {
                            sb.append("s");
                        } else if (Objects.equals(cells[x][y].getActor().getTileName(), "minotaur")) {
                            sb.append("m");
                        } else if (Objects.equals(cells[x][y].getActor().getTileName(), "ghost")) {
                            sb.append("g");
                        } else if (Objects.equals(cells[x][y].getActor().getTileName(), "player")) {
                            sb.append("@");
                        }
                    }
                    else  {
                        sb.append(".");
                    }
                }
                else if (cells[x][y].getType() == PORTAL) {
                    sb.append("p");
                } else if (cells[x][y].getType() == DOOR) {
                    sb.append("d");
                } else if (cells[x][y].getType() == EMPTY) {
                    sb.append(" ");
                } else if (cells[x][y].getType() == WALL) {
                    sb.append("#");
                } else if (cells[x][y].getType() == BUTTON) {
                    sb.append("b");
                }
            }
        }
        String table = sb.toString();
        return table;

    }

    public void createTxtForMap(String map,String saveName) throws IOException {

        String pathname = String.format("/home/marko/project/dungeon-crawl-2-java-markopeter/src/main/resources/map%s.txt", saveName);
        File mapFile = new File(pathname);

        FileOutputStream outputStream = new FileOutputStream(pathname);
        byte[] strToBytes = map.getBytes();
        outputStream.write(strToBytes);

        outputStream.close();

    }
}

