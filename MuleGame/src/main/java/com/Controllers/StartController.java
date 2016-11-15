package main.java.com.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.com.Data;
import main.java.com.DataHelper;

import java.io.IOException;

/**
 * Handles the start screen.
 */
public class StartController {

    /**
     * Sidenote:
     *
     * 1. Parent class (extends Node class) handles
     * all hierarchical scene graph operations,
     * including adding/removing child nodes,
     * marking branches dirty for layout and rendering, picking,
     * bounds calculations, and executing the layout pass on each pulse.
     *
     * 2. The JavaFX Scene class is the container for all content
     * in a scene graph.
     * The background of the scene is filled as specified by the fill
     * property.
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
     * Combobox with number of players.
     * Combo box = popup list of choices (num players).
     */
    @FXML
    private ComboBox numPlayers;
    /**
     * Combobox with number of difficulty.
     */
    @FXML
    private ComboBox difficulty;
    /**
     * Combobox with number of maps.
     */
    @FXML
    private ComboBox maps;


    /**
     * Start controller method loads the initial fxml, where difficulty
     * num of players, ...
     * Loads an object hierarchy from an XML document.
     * @throws IOException changing screens.
     */
    public StartController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource("../Views/sample.fxml"));
        fxmlLoader.setController(this);
        parent = fxmlLoader.load();
        scene = new Scene(parent, 800, 600);
    }

    /**
     * Go to player configuration stage. Method that calls the stage where
     * the player configuration is set.
     * @throws IOException changing screens.
     */
    public final void gotoPlayerConfig() throws IOException {
        Data.mapType = (String) maps.getSelectionModel().getSelectedItem();
        String diff1 = (String) difficulty.getSelectionModel()
                .getSelectedItem();
        diff1 = diff1.toLowerCase();
        Data.diff = diff1.equals("beginner") ? 1
                : diff1.equals("intermediate") ? 2 : 3;
        Data.size = ((Integer) numPlayers
                .getSelectionModel().getSelectedItem());
        PlayerController playerController = new PlayerController();
        playerController.callPlayerController(stage);
    }

    /**
     * Loads a game.
     * @throws Exception loading and changing screen
     */
    public final void loadGame() throws Exception {
        PlayerController playerController = new PlayerController();
        playerController.initSetup();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        DataHelper.read(fileChooser.showOpenDialog(stage));
        MapController mapController = new MapController();
        mapController.callMapNextTurn(stage);
    }

    /**
     * Launch start controller changes scene.
     * @param stage stage
     */
    public final void launchStartController(final Stage stage) {
        this.stage = stage;
        stage.setTitle("Config Screen");
        stage.setScene(scene);

        //Cannot be resized (false)
        stage.setResizable(false);
        stage.hide();
        stage.show();
    }


}
