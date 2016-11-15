package main.java.com.Models;

/**
 * Created by uddhav on 10/4/15.
 * Tile class with cost, owner, type, location fields
 */

public abstract class Tile {
    public int cost;
    public Player owner;
    public String type;
    public String location;
    public Mule mule;


    public double foodFactor;
    public double energyFactor;
    public double smithoreFactor;
    public double crystiteFactor;
    /**
     * Constructor that takes in cost, type of tile, and location of
     * the tile in the map
     * @param cost of the tile
     * @param type of the tile
     * @param location of the tile in the map, location of image file
     */
    public Tile(int cost, String type, String location) {
        this.cost = cost;
        this.owner = null;
        this.type = type;
        this.location = location;
        this.mule = null;
        this.foodFactor = 1.0;
        this.energyFactor = 1.0;
        this.smithoreFactor = 1.0;
        this.crystiteFactor = 1.0;
    }


    public double getFoodFactor() {
        return foodFactor;
    }

    public void setFoodFactor(double foodFactor) {
        this.foodFactor = foodFactor;
    }

    public double getEnergyFactor() {
        return energyFactor;
    }

    public void setEnergyFactor(double energyFactor) {
        this.energyFactor = energyFactor;
    }

    public double getSmithoreFactor() {
        return smithoreFactor;
    }

    public void setSmithoreFactor(double smithoreFactor) {
        this.smithoreFactor = smithoreFactor;
    }

    public double getCrystiteFactor() {
        return crystiteFactor;
    }

    public void setCrystiteFactor(double crystiteFactor) {
        this.crystiteFactor = crystiteFactor;
    }
}