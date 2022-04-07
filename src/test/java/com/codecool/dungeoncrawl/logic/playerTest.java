package com.codecool.dungeoncrawl.logic;
import com.codecool.dungeoncrawl.logic.actors.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Set;

import static com.codecool.dungeoncrawl.logic.CellType.FLOOR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
public class playerTest {
    private GameMap map = new GameMap(5,5, FLOOR);
    private Cell cell = new Cell(map,2,2,FLOOR);
    HashMap<Items, Integer> itemList = new HashMap<>();
    @Test
    void testHasTheKey_returnTrueOrFalse(){
        //Arrange
        Player player = new Player(cell);
        Key key = new Key(cell);
        String keyName = "key";
        player.addToInventory(key);
        //Act
        boolean expected = true;
        boolean actual = player.hasThatKey(keyName);
        //Assert
        assertEquals(expected, actual);
    }
    @Test
    void testAddItemToInventory_setDamage(){
        //Arrange
        Player player = new Player(cell);
        Swords swords = new Swords(cell);
        player.addToInventory(swords);
        //Act
        player.addToInventory(swords);
        boolean actual = player.getItemList().containsKey(swords);
        assertTrue(actual);
    }

    @Test
    void playerGetTileName_returnFalse (){
        //Arrange
        Player player = new Player(cell);
        player.setHealth(-11);
        //Act
        boolean actual = player.isAlive();
        //Assert
        assertFalse(actual);
    }

}
