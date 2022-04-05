package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Items;
import com.codecool.dungeoncrawl.logic.actors.Key;
import com.codecool.dungeoncrawl.logic.actors.Swords;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y, dmg, " +
                    "inventorySlot1Item, inventorySlot1Amount, inventorySlot2Item, inventorySlot2Amount) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getX());
            statement.setInt(4, player.getY());
            statement.setInt(5,player.getDmg());
            statement.setString(6, player.getInventorySlot1());
            statement.setInt(7,player.getInventorySlot1Amount());
            statement.setString(8, player.getInventorySlot2());
            statement.setInt(9,player.getInventorySlot2Amount());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {

    }

    @Override
    public PlayerModel get(int id) {
        return null;
    }

    @Override
    public HashMap<Items, Integer> getInventoryFromSql(int id){
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT inventorySlot1Item, inventorySlot1Amount, " +
                    "inventorySlot2Item, inventorySlot2Amount FROM player WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            HashMap<Items, Integer> itemList = new HashMap<>();
            switch(rs.getString(1)){
                case("key"):
                    itemList.put(new Key(null), rs.getInt(2));
                case("sword"):
                    itemList.put(new Swords(null), rs.getInt(2) );
            }
            switch(rs.getString(3)){
                case("key"):
                    itemList.put(new Key(null), rs.getInt(4));
                case("sword"):
                    itemList.put(new Swords(null), rs.getInt(4) );
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        return null;
    }
}
