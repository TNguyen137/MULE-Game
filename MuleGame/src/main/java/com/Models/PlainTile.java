package main.java.com.Models;

/**
 * Created by ibanar on 10/4/15.
 * Plain tile class to distinguish another type of tile in the game
 */

public class PlainTile extends Tile {

    /**
     * Constructor calls Tile (parent) constructor and sets
     * Cost, Type of tile, Image to be displayed
     */
    public PlainTile() {
        super(250, "Plain", "/main/java/com/img/PlainTile.png");
        this.setEnergyFactor(1.25);
    }
}
