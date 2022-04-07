package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }


    public void saveGameState(String currentMap, String saveText,int width, int height){
        GameState gameState = new GameState(currentMap, new Timestamp(System.currentTimeMillis()), saveText);
        gameStateDao.add(gameState,height, width);

    }

    public void overwriteGameState(GameState gs, PlayerModel pm){
        gameStateDao.update(gs);
        playerDao.update(pm);
    }

    public GameState getGameState(String name) {
        return gameStateDao.get(name);
    }
    public List<String> allName() {
        return gameStateDao.getAllPlayer();
    }

    public PGSimpleDataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(System.getenv().get("DATABASE_NAME"));
        dataSource.setUser(System.getenv().get("PSQL_USERNAME"));
        dataSource.setPassword(System.getenv().get("PSQL_PASSWORD"));
        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");
        return dataSource;
    }

    public GameStateDao getGameStateDao() {
        return gameStateDao;
    }

}
