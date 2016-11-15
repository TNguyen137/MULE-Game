package main.java.com.Models;

/**
 * Created by uddhav on 11/8/15.
 */
public class DesertTile extends Tile {

    /**
     * Constructor calls Tile (parent) constructor and sets
     * Cost, Type of tile, Image to be displayed
     */
    public DesertTile() {
        super(200, "Valley", "/main/java/com/img/DesertTile.png");
        this.setFoodFactor(0.25);
        this.setEnergyFactor(1.5);
    }
}
