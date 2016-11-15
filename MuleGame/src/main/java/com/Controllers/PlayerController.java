package main.java.com.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import main.java.com.Data;
import main.java.com.Models.HumanPlayer;
import main.java.com.Models.XergPlayer;
import main.java.com.Models.ZenomorphPlayer;
import main.java.com.Models.BlacksmithPlayer;
import main.java.com.Models.WorkerPlayer;
import main.java.com.Models.RiverTile;
import main.java.com.Models.RockyTile;
import main.java.com.Models.TownTile;
import main.java.com.Models.PlainTile;
import main.java.com.Models.DesertTile;
import main.java.com.Models.Tile;
import main.java.com.Models.ValleyTile;
import java.io.IOException;
import java.util.Random;

/**
 * Created by uddhav on 10/3/15.
 */
public class PlayerController {

    /**
     * Sidenote:
     *
     * 1. Parent class (extends Node class) handles all hierarchical
     * scene graph operations,
     * including adding/removing child nodes,
     * marking branches dirty for layout and rendering, picking,
     * bounds calculations, and executing the layout pass on each pulse.
     *
     * 2. The JavaFX Scene class is the container for all content
     * in a scene graph.
     * The background of the scene is filled as specified by
     * the fill property.
     * The application must specify the root Node for the scene graph by
     * setting the root property.
     *
     * 3. The JavaFX Stage class is the top level JavaFX container.
     * The primary Stage is constructed by the platform.
     * Additional Stage objects may be constructed by the application.
     * Stage objects must be constructed and modified on the JavaFX
     * Application Thread.
     */
    /**
     * Holder Parent.
     */
    private Parent parent;
    /**
     * Holder Scene.
     */
    private Scene scene;
    /**
     * Holder stage.
     */
    private Stage stage;

    /**
     * Textfield where you put your name at the initial fxml.
     */
    @FXML
    private TextField pName;

    /**
     * Combobox = list of choices (race of the player).
     */
    @FXML
    private ComboBox pRace;

    /**
     * List of color choices.
     */
    @FXML
    private ComboBox pColor;

    /**
     * Loads an object hierarchy from an XML document.
     * @throws IOException changing screen
     */
    public PlayerController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
            .getResource("../Views/Player-Config.fxml"));
        fxmlLoader.setController(this);
        parent = fxmlLoader.load();
        scene = new Scene(parent, 800, 600);
    }

    /**
     *
     * @param event event
     * @throws IOException changing screens
     */
    public final void initPlayer(final ActionEvent event) throws IOException {

        String color = (String) pColor.getSelectionModel().getSelectedItem();
        String race = (String) pRace.getSelectionModel().getSelectedItem();
        String name = pName.getText();
        race = race.toLowerCase();

        //Getting the Player
        switch (race) {
            case "xerg":
                Data.players[Data.playerLoopCounter]
                    = new XergPlayer(name, color);
                break;
            case "human":
                Data.players[Data.playerLoopCounter]
                    = new HumanPlayer(name, color);
                break;
            case "zenomorph":
                Data.players[Data.playerLoopCounter]
                    = new ZenomorphPlayer(name, color);
                break;
            case "worker":
                Data.players[Data.playerLoopCounter]
                    = new WorkerPlayer(name, color);
                break;
            case "blacksmith":
                Data.players[Data.playerLoopCounter]
                    = new BlacksmithPlayer(name, color);
                break;
            default:
                break;
        }

        //Adjusting for difficulty
        switch (Data.diff) {
            case 1:
                Data.players[Data.playerLoopCounter]
                    .setMoney((int) (Data.players[Data.playerLoopCounter]
                    .getMoney() * 1.25));
                Data.players[Data.playerLoopCounter]
                     .setFood((Data.players[Data.playerLoopCounter]
                     .getFood() + 2));
                Data.players[Data.playerLoopCounter]
                     .setEnergy((Data.players[Data.playerLoopCounter]
                     .getEnergy() + 2));
                break;
            case 2:
                break;
            case 3:
                Data.players[Data.playerLoopCounter]
                    .setMoney((int) (Data.players[Data.playerLoopCounter]
                    .getMoney() * 0.60));
                Data.players[Data.playerLoopCounter]
                    .setFood(Math.max(Data.players[Data.playerLoopCounter]
                    .getFood() - 2, 0));
                Data.players[Data.playerLoopCounter]
                    .setEnergy(Math.max(Data.players[Data.playerLoopCounter]
                    .getEnergy() - 1, 0));
                break;
            default:
                break;
        }
        System.out.println(Data.players[Data.playerLoopCounter]);
        System.out.println(Data.playerLoopCounter);

        if (Data.playerLoopCounter == Data.size - 1) {
            initSetup();
            MapController mapController = new MapController();
            mapController.callMapRound(stage);
        } else {
            Data.playerLoopCounter++;
            this.callPlayerController(stage);

        }
    }

    /**
     * Calls Player Controller.
     * @param stage stage
     */
    public final void callPlayerController(final Stage stage) {
        this.stage = stage;
        stage.setTitle("Set Player Config");
        stage.setScene(scene);
        stage.hide();
        stage.show();
    }

    /**
     * Initial Setup of the Game.
     */
    public final void initSetup() {
        Data.roundOrder = new int[Data.size];
        Data.setRoundNum(1);
        switch (Data.mapType.toLowerCase()) {
            case "standard" :
                for (int i = 0; i < Data.ROWS; i++) {
                    for (int j = 0; j < Data.COLUMNS; j++) {

                        Tile currentTile;
                        if (i == 2 && j == 4) {
                            currentTile = new TownTile();
                        } else if (j == 4) {
                            currentTile = new RiverTile();
                        } else if (i + j == 7 || i + j == 5 || i + j == 9) {
                            currentTile = new RockyTile();
                        } else {
                            currentTile = new PlainTile();
                        }
                        Data.tiles[i][j] = currentTile;
                        ImageView image = new ImageView(
                            new Image(currentTile.location));
                        AnchorPane pane = new AnchorPane(image);
                        pane.setPrefSize(200.0, 200.0);
                        Data.tilePanes[i][j] = pane;
                    }
                }
                break;
            case "desolate" :
                for (int i = 0; i < Data.ROWS; i++) {
                    for (int j = 0; j < Data.COLUMNS; j++) {

                        Tile currentTile;
                        Random rand = new Random();
                        int k = rand.nextInt(20);
                        if (i == 2 && j == 4) {
                            currentTile = new TownTile();
                        } else if (k < 8) {
                            currentTile = new PlainTile();
                        } else if (k < 11) {
                            currentTile = new RockyTile();
                        } else if (k < 14) {
                            currentTile = new RiverTile();
                        } else if (k < 17) {
                            currentTile = new DesertTile();
                        } else {
                            currentTile = new ValleyTile();
                        }
                        Data.tiles[i][j] = currentTile;
                        ImageView image = new ImageView(
                            new Image(currentTile.location));
                        AnchorPane pane = new AnchorPane(image);
                        pane.setPrefSize(200.0, 200.0);
                        Data.tilePanes[i][j] = pane;
                    }
                }
                break;
            case "hilly" :
                for (int i = 0; i < Data.ROWS; i++) {
                    for (int j = 0; j < Data.COLUMNS; j++) {

                        Tile currentTile;
                        Random rand = new Random();
                        int k = rand.nextInt(20);
                        if (i == 2 && j == 4) {
                            currentTile = new TownTile();
                        } else if (k < 8) {
                            currentTile = new RockyTile();
                        } else if (k < 11) {
                            currentTile = new RiverTile();
                        } else if (k < 14) {
                            currentTile = new PlainTile();
                        } else if (k < 17) {
                            currentTile = new DesertTile();
                        } else {
                            currentTile = new ValleyTile();
                        }
                        Data.tiles[i][j] = currentTile;
                        ImageView image = new ImageView(
                            new Image(currentTile.location));
                        AnchorPane pane = new AnchorPane(image);
                        pane.setPrefSize(200.0, 200.0);
                        Data.tilePanes[i][j] = pane;
                    }
                }
                break;
            default:
                for (int i = 0; i < Data.ROWS; i++) {
                    for (int j = 0; j < Data.COLUMNS; j++) {

                        Tile currentTile;
                        Random rand = new Random();
                        int k = rand.nextInt(15);
                        if (i == 2 && j == 4) {
                            currentTile = new TownTile();
                        } else if (k < 3) {
                            currentTile = new RiverTile();
                        } else if (k < 7) {
                            currentTile = new RockyTile();
                        } else if (k < 11) {
                            currentTile = new PlainTile();
                        } else if (k < 13) {
                            currentTile = new DesertTile();
                        } else {
                            currentTile = new ValleyTile();
                        }
                        Data.tiles[i][j] = currentTile;
                        ImageView image = new ImageView(
                            new Image(currentTile.location));
                        AnchorPane pane = new AnchorPane(image);
                        pane.setPrefSize(200.0, 200.0);
                        Data.tilePanes[i][j] = pane;
                    }
                }
                break;
        }

    }
}
