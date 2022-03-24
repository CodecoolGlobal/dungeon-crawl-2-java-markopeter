package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Items;
import com.codecool.dungeoncrawl.logic.actors.Swords;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import static com.codecool.dungeoncrawl.logic.CellType.FLOOR;

public class Main extends Application {
    GameMap map = MapLoader.loadMap(1);
    int canvasSize = 31;
    Canvas canvas = new Canvas(
            canvasSize * Tiles.TILE_WIDTH,
            canvasSize * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label damageLabel = new Label();
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
        Button breakButton = new Button();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000),
                (evt) -> {
                map.moveEnemy();
                refresh();
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        breakButton.setText("Break !");
        breakButton.setFocusTraversable(false);
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

        breakButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int x = map.getPlayer().getX();
                int y = map.getPlayer().getY();
                HashMap<Items, Integer> itemList = map.getPlayer().getItemList();
                boolean hasClass = itemList.keySet().stream().anyMatch(Swords.class::isInstance);
                if (hasClass) {

                if (Objects.equals(map.getCell(x + 1, y).getTileName(), "wall")
                ) {
                    map.getCell(x + 1, y).setType(FLOOR);
                    refresh();
                } else if (Objects.equals(map.getCell(x - 1, y).getTileName(), "wall")
                ) {
                    map.getCell(x - 1, y).setType(FLOOR);
                    refresh();
                }
                else if (Objects.equals(map.getCell(x , y + 1).getTileName(), "wall")
                ) {
                    map.getCell(x, y + 1).setType(FLOOR);
                    refresh();
                }
                else if (Objects.equals(map.getCell(x , y -1).getTileName(), "wall")
                ) {
                    map.getCell(x - 1, y + 1).setType(FLOOR);
                    refresh();
                }
            }
        }
        });
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.add(pushButton, 1,6);
        ui.add(pickUpButton, 0,6);
        ui.add(breakButton, 0,8);
        ui.add(new Label("Health: "), 0, 0);
        ui.add(new Label("Damage: "), 0, 1);
        ui.add(new Label("Inventory: "), 0, 3);
        ui.add(healthLabel, 1, 0);
        ui.add(damageLabel, 1, 1);
        int inventoryItemStartCoordinate = 4;
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
        Popup.display("Welcome on board !", "Greetings traveler ! Dont get be confused by your " +
                "little sword, you stand no chance against this dungeon mighty creatures !");
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if(map.getPlayer().isAlive()){
            switch (keyEvent.getCode()) {

                case UP:
                    map.getPlayer().checkForCollision(0, -1);
                    if(map.getPlayer().isOnPortal()){
                        if(gameEnd()){
                            System.exit(0);
                        }
                        map =MapLoader.loadMap(2);

                    }
                    refresh();
                    break;
                case DOWN:
                    map.getPlayer().checkForCollision(0, 1);
                    if(map.getPlayer().isOnPortal()){
                        if(gameEnd()){
                            System.exit(0);
                        }
                        map =MapLoader.loadMap(2);
                    }
                    refresh();
                    break;
                case LEFT:
                    map.getPlayer().checkForCollision(-1, 0);
                    if(map.getPlayer().isOnPortal()){
                        if(gameEnd()){
                            System.exit(0);
                        }
                        map =MapLoader.loadMap(2);
                    }
                    refresh();
                    break;
                case RIGHT:
                    map.getPlayer().checkForCollision(1,0);
                    if(map.getPlayer().isOnPortal()){
                        if(gameEnd()){
                            System.exit(0);
                        }
                        map =MapLoader.loadMap(2);
                    }
                    refresh();
                    break;
            }
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
        int mapYStart = map.getPlayer().getY()-(canvasSize-1)/2;
        for(int i = 0; i < canvasSize; i++){
            int mapXStart = map.getPlayer().getX()-(canvasSize-1)/2;
            for(int j = 0; j < canvasSize; j++){
                if(!(mapXStart < 0 || mapXStart >= map.getWidth() || mapYStart < 0 || mapYStart >= map.getHeight())){
                    Cell cell = map.getCell(mapXStart, mapYStart);
                    if (cell.getActor() != null) {
                        Tiles.drawTile(context, cell.getActor(), j, i);
                    }else if(cell.getItem() != null){
                        Tiles.drawTile(context, cell.getItem(), j, i);
                    }
                    else {
                        Tiles.drawTile(context, cell, j, i);
                    }
                }else{
                    Cell cell = map.getCell(0,13);
                    Tiles.drawTile(context, cell, j, i);
                }
                mapXStart++;
            }
            mapYStart++;
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        damageLabel.setText("" + map.getPlayer().getDamage());
        if(!map.getPlayer().isAlive()){
            healthLabel.setText("Dead!");
        }
    }

    public boolean gameEnd(){
        HashMap<Items, Integer> itemList = map.getPlayer().getItemList();
        boolean hasClass = itemList.keySet().stream().anyMatch(Swords.class::isInstance);
        if (hasClass) {
            return true;
        }
        return false;
    }
}
