package main.java.com.Models;

/**
 * Created by uddhav on 10/4/15.
 */
public class RockyTile extends Tile {

    /**
     * Constructor for the rocky tile. It contains the cost, the type (Rocky)
     * and the image location (image with rocks)
     */
    public RockyTile() {
        super(300, "Rocky", "/main/java/com/img/RockyTile.png");
        this.setCrystiteFactor(1.25);
        this.setSmithoreFactor(1.25);
    }
}