package main.java.com;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import main.java.com.Controllers.StartController;

import java.net.URL;

/**
 * Class that runs the game.
 */
public class PlanetMULE extends Application {

    /**
     * Declares a static MediaPLayer so that music can be played throughout the game.
     */
    public static MediaPlayer mediaPlayer;

    /**
     * Method that know how to setup and it fplays music.
     */
    public void music(){
        URL resource = getClass().getResource("music/planet_mule.mp3");
        Media media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    /**
     * Main method to run the game.
     * @param args arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        //Loads an object hierarchy from an XML document.
        StartController startController = new StartController();

        music();

        startController.launchStartController(primaryStage);
    }



}
