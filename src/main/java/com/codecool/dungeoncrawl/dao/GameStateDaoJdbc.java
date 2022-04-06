package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void add(GameState state, int width, int height) {
        try(Connection conn = dataSource.getConnection()){
            String sql = "INSERT INTO game_state (save_text, current_map, saved_at, height, width) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, state.getSaveText());
            st.setString(2, state.getCurrentMap());
            st.setTimestamp(3,state.getSavedAt());
            st.setInt(4, height);
            st.setInt(5, width);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            state.setId(rs.getInt(1));
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(GameState state) {

    }
    @Override
    public GameState get(String name) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "SELECT save_text, current_map, saved_at FROM game_state WHERE save_text = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, name);
                ResultSet rs = st.executeQuery();
                if (!rs.next()) {
                    return null;
                }
                return new GameState(rs.getString(2),rs.getTimestamp(3), rs.getString(1));
            } catch (SQLException e) {
                throw new RuntimeException("Error while reading player with name: " + name, e);
            }
        }

    @Override
    public List<GameState> getAll() {
        return null;
    }

    public List<String> getAllPlayer() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT save_text FROM game_state ";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<String> saveTexts = new ArrayList<>();
            while(rs.next()){
                saveTexts.add(rs.getString(1));
            }
            return saveTexts;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading player with name: " +  e);
        }
    }

}
