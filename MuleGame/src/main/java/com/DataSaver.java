package main.java.com;

import javafx.beans.property.IntegerProperty;
import javafx.scene.layout.AnchorPane;
import main.java.com.Models.Player;
import main.java.com.Models.Tile;
import main.java.com.Models.Turn;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by uddhav on 11/3/15.
 */
public class DataSaver {
    public  Player[] players = new Player[4];
    public int size;
    public int playerLoopCounter;
    public final int COLUMNS = 9;
    public final int ROWS = 5;
    public IntegerProperty roundNum;
    public IntegerProperty turnNum;
    public Turn currentTurn;
    public int[] roundOrder;
    public AnchorPane[][] tilePanes = new AnchorPane[ROWS][COLUMNS];
    public Tile[][] tiles = new Tile[ROWS][COLUMNS];

    
    public ArrayList getData() {
        ArrayList arraylist = new ArrayList();

        players = Data.players;
        size = Data.size;
        playerLoopCounter = Data.playerLoopCounter;
        roundNum = Data.roundNumProperty();
        turnNum = Data.turnNumProperty();
        currentTurn = Data.currentTurn;
        roundOrder = Data.roundOrder;
        tilePanes = Data.tilePanes;
        tiles = Data.tiles;

        arraylist.addAll(Arrays.asList(players, size, playerLoopCounter, roundNum,
                turnNum, currentTurn, roundOrder, tilePanes, tiles));
        return arraylist;

    }
}
