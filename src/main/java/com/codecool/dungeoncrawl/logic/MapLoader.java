package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(int level) {
        InputStream is = MapLoader.class.getResourceAsStream(String.format("/map%s.txt", level));
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        List<Actor> enemies = map.getEnemies();
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            cell.setItem(new Swords(cell));
                            break;
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'd':
                            cell.setType(CellType.DOOR);
                            break;
                        case 'p':
                            cell.setType(CellType.PORTAL);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.setActor(new Skeleton(cell));
                            map.addEnemy(new Skeleton(cell));
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            map.setActor(new Ghost(cell));
                            map.addEnemy(new Ghost(cell));
                            break;
                        case 'b':
                            cell.setType(CellType.BUTTON);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            cell.setItem(new Key(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
