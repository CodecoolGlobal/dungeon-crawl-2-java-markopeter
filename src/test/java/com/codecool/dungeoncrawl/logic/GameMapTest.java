package com.codecool.dungeoncrawl.logic;
import com.codecool.dungeoncrawl.logic.actors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.codecool.dungeoncrawl.logic.CellType.FLOOR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameMapTest {
    @Mock
    private Skeleton skeleton ;
    @Mock
    private Ghost ghost;
    @Mock
    private Swords swords;

    @BeforeEach
    void setUp() {
        GameMap map = new GameMap(5,5, FLOOR);
    }


    @Test
    void gameMapIntoString(){
        // Arrange
        GameMap map = new GameMap(5,5, FLOOR);
        Cell[][] cells = new Cell[2][2];
        cells[0][0] = new Cell(map, 0,0,FLOOR);
        cells[0][0].setType(FLOOR);
        cells[0][0].setItem(swords);
        int x = map.getWidth();
        int y = map.getHeight();
        //Act
        String expected = "5 5\n.....\n" +
                ".....\n" +
                ".....\n" +
                ".....\n" +
                ".....";
        String actual = map.convertGameMapToString();
        assertEquals(expected,actual);
    }

}
