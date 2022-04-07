package com.codecool.dungeoncrawl.logic;
import com.codecool.dungeoncrawl.logic.actors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static com.codecool.dungeoncrawl.logic.CellType.FLOOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class MinotaurTest {
    GameMap map = new GameMap(5,5,FLOOR);
    @Test
    void minotaurMoveTest_XYCoordinates(){
        Cell cell = new Cell(map,2,2, FLOOR);
        Minotaur minotaur = new Minotaur(cell);
        int [] actual = minotaur.minotaurMove(0,0);
        boolean first = actual[0] == -1;
        boolean second = actual[1] == -1;
        assertTrue(first && second);

    }
    @Test
    void getDamage_ReturnIntDamage(){
        Cell cell = new Cell(map,2,2, FLOOR);
        Minotaur minotaur = new Minotaur(cell);
        minotaur.setDamage(0);
        int actual = minotaur.getDamage();
        int expected = 0;
        assertEquals(expected,actual);

    }

}
