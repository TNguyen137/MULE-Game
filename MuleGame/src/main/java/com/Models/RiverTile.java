package main.java.com.Models;

/**
 * Created by ibanar on 10/4/15.
 * Rivertile
 */
public class RiverTile extends Tile {

    /**
     *Constructor that calls Tile constructor (Cost, type, image)
     */
    public RiverTile() {
        super(400, "River", "/main/java/com/img/RiverTile.png");
        this.setFoodFactor(1.50);
    }
}
