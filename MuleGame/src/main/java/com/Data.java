package main.java.com;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.AnchorPane;
import main.java.com.Models.Player;
import main.java.com.Models.Tile;
import main.java.com.Models.Turn;

/**
 * Created by uddhav on 10/1/15.
 * Class created in order to control all the data of the game
 */

public class Data {
    public static double factor;
    public static int diff;
    public static String mapType;
    public static Player[] players = new Player[4];

    //Size of the number of players??
    public static int size;

    //This is simply for getting information of all the Players
    public static int playerLoopCounter;


    //Fixed Stuff for the structure of the map
    public static final int COLUMNS = 9;
    public static final int ROWS = 5;

    /**
     * Game Statistics. Round number, and turn number
     * Round number = Increases until reached 13 when the game ends
     * Turn number = used to keep track of the number of turns (max = 4)
     *               1 per each player
     */
    public static IntegerProperty roundNum = new SimpleIntegerProperty(0);

    public static IntegerProperty turnNum = new SimpleIntegerProperty(0);

    //Turn Data
    public static Turn currentTurn = new Turn();

    /**
     * Round Data. Used in order to determine how advantages
     * and disadvantages work depending on the position of each player
     * (roundOrder)
     */
    public static int[] roundOrder;

    /**
     * Anchorpane tilePanes = where all the images are inserted (initialized with constants
     *                        ROWS and COLUMNS
     * Tiles tiles = tiles are initialized (matrix with constants ROWS & COLUMNS)
     */
    public static AnchorPane[][] tilePanes = new AnchorPane[ROWS][COLUMNS];
    public static Tile[][] tiles = new Tile[ROWS][COLUMNS];
    /**
     * Getter method for the number of turns that the game is undergoing.
     * @return the number of turns occurred in the game
     */
    public static int getTurnNum() {
        return turnNum.get();
    }

    /**
     * Turn number property. Does same as function above
     * @return the turn number
     */
    public static IntegerProperty turnNumProperty() {
        return turnNum;
    }

    /**
     * Sets the turn number to whatever we pass as parameter
     * @param turnNum we want to change to
     */
    public static void setTurnNum(final int turnNum) {
        Data.turnNum.set(turnNum);
    }

    /**
     * Getter method for the round number.
     * @return the round number
     */
    public static int getRoundNum() {
        return roundNum.get();
    }

    /**
     * Same as function above but with property class
     * @return round number
     */
    public static IntegerProperty roundNumProperty() {
        return roundNum;
    }

    /**
     * Set the round number
     * @param roundNum to be set to.
     */
    public static void setRoundNum(final int roundNum) {
        Data.roundNum.set(roundNum);
    }

}
