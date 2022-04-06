package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Items;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int x;
    private int y;
    private int dmg;
    private String inventorySlot1;
    private String inventorySlot2;
    private int inventorySlot1Amount;
    private int inventorySlot2Amount;

    public String getInventorySlot1() {
        return inventorySlot1;
    }

    public String getInventorySlot2() {
        return inventorySlot2;
    }

    public int getInventorySlot1Amount() {
        return inventorySlot1Amount;
    }

    public int getInventorySlot2Amount() {
        return inventorySlot2Amount;
    }

    public PlayerModel(String playerName, int x, int y, int dmg,
                       String inventorySlot1, String inventorySlot2,
                       int inventorySlot1Amount, int inventorySlot2Amount) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
        this.dmg = dmg;
        this.inventorySlot1 = inventorySlot1;
        this.inventorySlot1Amount = inventorySlot1Amount;
        this.inventorySlot2 = inventorySlot2;
        this.inventorySlot2Amount = inventorySlot2Amount;
    }

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();

        this.hp = player.getHealth();
        this.dmg = player.getDamage();
        Object[] items = player.getItemList().keySet().toArray();
        switch (player.getItemList().size()){
            case(0):
                this.inventorySlot1 = "None";
                this.inventorySlot1Amount = 0;
                this.inventorySlot2 = "None";
                this.inventorySlot2Amount = 0;
            case(1):
                Items firstItem = (Items)items[0];
                this.inventorySlot1 = firstItem.getName();
                this.inventorySlot1Amount = player.getItemList().get(firstItem);
                this.inventorySlot2 = "None";
                this.inventorySlot2Amount = 0;
            case (2):
                firstItem = (Items)player.getItemList().keySet().toArray()[0];
                this.inventorySlot1 = firstItem.getName();
                this.inventorySlot1Amount = player.getItemList().get(firstItem);
                Items secondItem = (Items)items[1];
                this.inventorySlot2 = secondItem.getName();
                this.inventorySlot2Amount = player.getItemList().get(secondItem);

        }


    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getDmg(){return dmg;}

    public void setDmg(int dmg){ this.dmg = dmg;}

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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
}
