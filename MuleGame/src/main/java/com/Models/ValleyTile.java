package main.java.com.Models;

/**
 * Created by uddhav on 11/8/15.
 */

public class ValleyTile extends Tile {

    /**
     * Constructor calls Tile (parent) constructor and sets
     * Cost, Type of tile, Image to be displayed
     */
    public ValleyTile() {
        super(200, "Valley", "/main/java/com/img/ValleyTile.png");
        this.setFoodFactor(2.00);
        this.setEnergyFactor(0.5);
        this.setCrystiteFactor(0.5);
        this.setSmithoreFactor(0.5);
    }
}