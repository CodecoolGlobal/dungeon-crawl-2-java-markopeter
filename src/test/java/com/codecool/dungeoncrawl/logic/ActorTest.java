package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(player.getCell().getNeighbor(1,0));

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(null, gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        player.checkForCollision(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(2, 1));
        player.checkForCollision(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoAnotherActor() {     //Player gets kicked back by 1 unit in the event of collision
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.checkForCollision(1, 0);

        assertEquals(0, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void fightingActorsTakeDamage(){
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        int healthOfAttackerBeforeFight = player.getHealth();
        int healthOfAttackedBeforeFight = skeleton.getHealth();
        player.fight(player,skeleton);
        int healthOfAttackerAfterFight = player.getHealth();
        int healthOfAttackedAfterFight = skeleton.getHealth();
        assertTrue(healthOfAttackedAfterFight < healthOfAttackedBeforeFight && healthOfAttackerAfterFight < healthOfAttackerBeforeFight);
    }

    @Test
    void actorTakesDamage(){
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        int healthBeforeDamage = skeleton.getHealth();
        skeleton.takeDamage(2);
        int healthAfterDamage = skeleton.getHealth();
        assertTrue(healthBeforeDamage > healthAfterDamage);
    }

    @Test
    public void actorIsAlivePositive(){
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        assertTrue(skeleton.isAlive());
    }

    @Test
    public void actorIsAliveNegative(){
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        skeleton.takeDamage(skeleton.getHealth()+1);
        assertFalse(skeleton.isAlive());
    }
}