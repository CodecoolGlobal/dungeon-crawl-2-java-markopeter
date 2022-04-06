package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public interface GameStateDao {
    void add(GameState state, int width, int height);
    void update(GameState state);
    GameState get(String name);
    List<GameState> getAll();
    List<String>getAllPlayer();
}
