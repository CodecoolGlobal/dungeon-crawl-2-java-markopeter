package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Items;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import static com.codecool.dungeoncrawl.logic.CellType.FLOOR;

public class Main extends Application {
    GameMap map = MapLoader.loadMap(1);
    Canvas canvas = new Canvas(
            40 * Tiles.TILE_WIDTH,
            40 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label slot1 = new Label();
    Label slot2 = new Label();
    Label[] inventorySlots = {slot1,slot2};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        Button pickUpButton = new Button();
        Button pushButton = new Button();
        pushButton.setText("Push !");
        pushButton.setFocusTraversable(false);
        pickUpButton.setText("Pick up item!");
        pickUpButton.setFocusTraversable(false);
        pickUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int x = map.getPlayer().getX();
                int y = map.getPlayer().getY();
                map.getPlayer().pickUpItem();
                map.getCell(x, y).setItem(null);
                addItemsToUI();
            }
        });
        pushButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int x = map.getPlayer().getX();
                int y = map.getPlayer().getY();
                if(Objects.equals(map.getCell(x , y).getTileName(), "button")
                ){
                    map.getCell(5, 5).setType(FLOOR);
                    refresh();
                }
            }
        });
        ui.setPrefWidth(300);
        ui.setPrefHeight(1000);
        ui.setPadding(new Insets(10));
        ui.add(pushButton, 0,10);
        ui.add(pickUpButton, 0,3);
        ui.add(new Label("Health: "), 0, 0);
        ui.add(new Label("Inventory: "), 0, 4);
        ui.add(healthLabel, 1, 0);
        int inventoryItemStartCoordinate = 5;
        for(Label label: inventorySlots){
            ui.add(label, 0, inventoryItemStartCoordinate);
            label.setText("empty");
            inventoryItemStartCoordinate++;
        }

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getCode()) {
           
            case UP:
                map.getPlayer().checkForCollision(0, -1);
                map.moveEnemy();
                refresh();
                break;
            case DOWN:
                map.getPlayer().checkForCollision(0, 1);
                map.moveEnemy();
                refresh();
                break;
            case LEFT:
                map.getPlayer().checkForCollision(-1, 0);
                map.moveEnemy();
                refresh();
                break;
            case RIGHT:
                map.getPlayer().checkForCollision(1,0);
                map.moveEnemy();
                refresh();
                break;
        }
    }

    private void addItemsToUI(){
        HashMap<Items, Integer> itemList = map.getPlayer().getItemList();
        Set<Items> items = itemList.keySet();
        int firstEmptySlot = 0;
        for(Items key: items){
            inventorySlots[firstEmptySlot].setText(key.getName() + ": " + itemList.get(key));
            firstEmptySlot++;
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                }else if(cell.getItem() != null){
                    Tiles.drawTile(context, cell.getItem(), x, y);
                }
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
    }
}
