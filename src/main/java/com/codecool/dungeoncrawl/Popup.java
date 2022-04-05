package com.codecool.dungeoncrawl;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.GameStateDaoJdbc;
import com.codecool.dungeoncrawl.model.GameState;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

//    public static void saveDisplay(String title, String message){
//        Stage saveWindow = new Stage();
//        GameDatabaseManager databaseManager = new GameDatabaseManager();
//        saveWindow.initModality(Modality.APPLICATION_MODAL);
//        saveWindow.setTitle(title);
//        saveWindow.setMinWidth(300);
//
//        Label label = new Label();
//        label.setText(message);
//
//        Button closeButton = new Button("Got it ! ");
//        closeButton.setOnAction(e -> saveWindow.close());
//        Button saveButton = new Button("Save");
//        saveButton.setOnAction(e -> state.add(save));
//
//        VBox layout = new VBox(10);
//        layout.getChildren().addAll(label, closeButton);
//        layout.setAlignment(Pos.CENTER);
//        Scene scene = new Scene(layout);
//        saveWindow.setScene(scene);
//        saveWindow.showAndWait();
//
//    }
}
