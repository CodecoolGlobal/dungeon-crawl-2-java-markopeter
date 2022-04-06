package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Items;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Swords;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import static com.codecool.dungeoncrawl.logic.CellType.FLOOR;

public class Main extends Application {
    GameMap map = MapLoader.loadMap("1");
    int canvasSize = 31;
    Canvas canvas = new Canvas(
            canvasSize * Tiles.TILE_WIDTH,
            canvasSize * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    GameDatabaseManager dbManager;
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
        setupDbManager();
        GridPane ui = new GridPane();
        Button pickUpButton = new Button();
        Button pushButton = new Button();
        Button breakButton = new Button();
        Button inputButton = new Button();
        Button loadButton = new Button();
        Label nameLabel = new Label("Username");
        ProgressBar healthBar = new ProgressBar();
        healthBar.setProgress(1);

        ui.add(nameLabel,0,12);
        ui.add(healthBar,0,18);
        ui.add(loadButton,0,22);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000),
                (evt) -> {
                map.moveEnemy();
                refresh();
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        loadButton.setText("Load");
        loadButton.setFocusTraversable(false);
        inputButton.setText("Click to save!");
        inputButton.setFocusTraversable(false);
        breakButton.setText("Break !");
        breakButton.setFocusTraversable(false);
        pushButton.setText("Push !");
        pushButton.setFocusTraversable(false);
        pickUpButton.setText("Pick up item!");
        pickUpButton.setFocusTraversable(false);

        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(actionEvent.getSource()==loadButton){
                    GameDatabaseManager game = new GameDatabaseManager();
                    try {
                        game.setup();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    String loadName = game.getGameState(map.getPlayer().getName()).getSaveText();
                    map = MapLoader.loadMap(loadName);
                    refresh();
                }
            }
        });

        inputButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Popup.saveModal("Save game!", "Add your name to save!", map);
            }
        });


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
        ui.setPrefWidth(300);
        ui.setPadding(new Insets(10));
        ui.add(pushButton, 0,10);
        ui.add(pickUpButton, 0,6);
        ui.add(breakButton, 0,8);
        ui.add(inputButton, 0, 16);
        ui.add(new Label("Health: "), 0, 1);
        ui.add(new Label("Damage: "), 0, 2);
        ui.add(new Label("Inventory: "), 0, 3);
        ui.add(healthLabel, 1, 1);
        ui.add(damageLabel, 1, 2);
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
        scene.setOnKeyReleased(this::onKeyReleased);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        Popup.display("Welcome on board !", "Greetings traveler ! Dont get be confused by your " +
                "little sword, you stand no chance against this dungeon mighty creatures !");
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if(map.getPlayer().isAlive()){
            switch (keyEvent.getCode()) {

                case UP:
                    map.getPlayer().checkForCollision(0, -1);
                    if(map.getPlayer().isOnPortal()){
                        if(gameEnd()){
                            exit();
                        }
                        map =MapLoader.loadMap("2");

                    }
                    refresh();
                    break;
                case DOWN:
                    map.getPlayer().checkForCollision(0, 1);
                    if(map.getPlayer().isOnPortal()){
                        if(gameEnd()){
                            exit();
                        }
                        map =MapLoader.loadMap("2");
                    }
                    refresh();
                    break;
                case LEFT:
                    map.getPlayer().checkForCollision(-1, 0);
                    if(map.getPlayer().isOnPortal()){
                        if(gameEnd()){
                            exit();
                        }
                        map =MapLoader.loadMap("2");
                    }
                    refresh();
                    break;
                case RIGHT:
                    map.getPlayer().checkForCollision(1,0);
                    if(map.getPlayer().isOnPortal()){
                        if(gameEnd()){
                            exit();
                        }
                        map =MapLoader.loadMap("2");
                    }
                    refresh();
                    break;
                case S:
                    Player player = map.getPlayer();
                    dbManager.savePlayer(player);
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

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
