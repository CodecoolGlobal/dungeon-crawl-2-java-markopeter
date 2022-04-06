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
        saveButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PlayerModel player = new PlayerModel(map.getPlayer());
                if (actionEvent.getSource() == saveButton) {
                    GameDatabaseManager saveGame = new GameDatabaseManager();

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
                                System.out.println("Test overwrite");
                            }
                       }
                    map.getPlayer().setName(inputField.getText());



                    saveGame.saveGameState(stringState, map.getPlayer().getName(), map.getHeight(), map.getWidth());

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

}
