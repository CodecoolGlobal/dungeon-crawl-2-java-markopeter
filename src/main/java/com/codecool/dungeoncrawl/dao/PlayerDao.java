package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Items;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.HashMap;
import java.util.List;

public interface PlayerDao {
    void add(PlayerModel player);
    void update(PlayerModel player);
    PlayerModel get(int id);
    HashMap<Items, Integer> getInventoryFromSql(int id);
    int getPlayerHealthFromSql(int id);
    List<PlayerModel> getAll();
}
