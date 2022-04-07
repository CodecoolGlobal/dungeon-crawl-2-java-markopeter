package com.codecool.dungeoncrawl;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.GameStateDaoJdbc;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Popup {

    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Got it ! ");
        closeButton.setOnAction(e -> window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void saveModal (String title, String message, GameMap map){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        Label saveName = new Label();
        TextField inputField = new TextField();
        Button saveButton = new Button("Save ! ");
        GameState gs = new GameState(map.convertGameMapToString(), new Timestamp(System.currentTimeMillis()), map.getPlayer().getName());
        PlayerModel player = new PlayerModel(map.getPlayer());
        gs.setPlayer(player);
        saveButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (actionEvent.getSource() == saveButton) {
                    GameDatabaseManager saveGame = new GameDatabaseManager();
                    boolean overWritten = false;
                    try {
                        saveGame.setup();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    String stringState = map.convertGameMapToString();
                    map.getPlayer().setName(inputField.getText());
                    List<String> allNames = saveGame.allName();
                    for(String name: allNames){
                        if(Objects.equals(name, map.getPlayer().getName())){
                            GameState currentGs = new GameState(map.convertGameMapToString(), new Timestamp(System.currentTimeMillis()), map.getPlayer().getName());
                            PlayerModel cPlayer = new PlayerModel(map.getPlayer());
                            currentGs.setPlayer(cPlayer);
                            overWriteModal("Overwrite", "Would you like to overwrite?", currentGs, new PlayerModel(map.getPlayer()));
                            overWritten = true;
                        }
                   }
                    if(!overWritten){
                        saveGame.saveGameState(stringState, map.getPlayer().getName(), map.getHeight(), map.getWidth());
                        saveGame.savePlayer(map.getPlayer());
                    }
                }
            }
        });

        label.setText(message);
        Button closeButton = new Button("Close ! ");
        closeButton.setOnAction(e -> window.close());
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label,inputField,saveName,saveButton,closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        map.getPlayer().setName(inputField.getText());
        inputField.setDisable(true);
    }

    public static void overWriteModal(String title, String message, GameState gs, PlayerModel pm){
        Stage overwriteWindow = new Stage();
        overwriteWindow.initModality(Modality.APPLICATION_MODAL);
        overwriteWindow.setTitle(title);
        overwriteWindow.setMinWidth(250);
        overwriteWindow.setMinHeight(250);
        Label label = new Label(message);
        Label saveName = new Label();
        TextField inputField = new TextField();
        Button yesButton = new Button("Yes! ");
        Button noButton = new Button("No !");
        yesButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GameDatabaseManager gdm = new GameDatabaseManager();
                try {
                    gdm.setup();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                gdm.overwriteGameState(gs,pm);
                overwriteWindow.close();
            }
            });
        noButton.setOnAction(e -> overwriteWindow.close());
        VBox layout = new VBox(30);
        layout.getChildren().addAll(label,yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        overwriteWindow.setScene(scene);
        overwriteWindow.showAndWait();

    }
}
