package main.java.com.Models;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import main.java.com.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by lilTjeezy on 9/18/2015.
 */

public abstract class Player {

    public String name;
    public String race;
    public String color;
    private IntegerProperty money;
    private IntegerProperty smithore;
    private IntegerProperty food;
    private IntegerProperty crystite;
    private IntegerProperty energy;
    private NumberBinding total;
    private IntegerProperty assets;
    private double foodFact;
    private double crystiteFact;
    private double energyFact;
    private double smithoreFact;


    public Player (String n, String r, String c, int money, int energy, int food) {
        this.name = n;
        this.race = r;
        this.color = c;
        this.money = new SimpleIntegerProperty(money);
        this.crystite = new SimpleIntegerProperty(0);
        this.energy = new SimpleIntegerProperty(4);
        this.food = new SimpleIntegerProperty(8);
        this.smithore = new SimpleIntegerProperty(0);
        this.assets = new SimpleIntegerProperty(0);
        total = Bindings.add(money, assets);
        foodFact = 1.0;
        crystiteFact = 1.0;
        energyFact = 1.0;
        smithoreFact = 1.0;
    }

    /**
     * Getter method for the money of the player
     * @return the money of the player
     */
    public int getMoney() {
        return money.get();
    }

    /**
     * Method that returns his money
     * @return money
     */
    public IntegerProperty moneyProperty() {
        return money;
    }

    /**
     * Setter method for the money
     * @param money that the player has
     */
    public void setMoney(int money) {
        this.money.set(money);
    }

    /**
     * Getter method for the smithore
     * @return smithore
     */
    public int getSmithore() {
        return smithore.get();
    }

    /**
     * Getter method for the smithore in integerproperty
     * @return smithore property
     */
    public IntegerProperty smithoreProperty() {
        return smithore;
    }

    /**
     * Setter method for the smithore
     * @param smithore to be set
     */
    public void setSmithore(int smithore) {
        this.smithore.set(smithore);
    }

    /**
     * Getter method for the food
     * @return food
     */
    public int getFood() {
        return food.get();
    }

    /**
     * Return integer property
     * @return
     */
    public IntegerProperty foodProperty() {
        return food;
    }

    /**
     *
     * @param food
     */
    public void setFood(int food) {
        this.food.set(food);
    }

    /**
     *
     * @return
     */
    public int getCrystite() {
        return crystite.get();
    }

    /**
     *
     * @return
     */
    public IntegerProperty crystiteProperty() {
        return crystite;
    }

    public void setCrystite(int crystite) {
        this.crystite.set(crystite);
    }

    public int getEnergy() {
        return energy.get();
    }

    public IntegerProperty energyProperty() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy.set(energy);
    }

    public NumberBinding totalProperty() {
        return total;
    }

    public int getAssets() {
        return assets.get();
    }

    public IntegerProperty assetsProperty() {
        return assets;
    }

    public void setAssets(int assets) {
        this.assets.set(assets);
    }


    public double getFoodFact() {
        return foodFact;
    }

    public void setFoodFact(double foodFact) {
        this.foodFact = foodFact;
    }

    public double getCrystiteFact() {
        return crystiteFact;
    }

    public void setCrystiteFact(double crystiteFact) {
        this.crystiteFact = crystiteFact;
    }

    public double getEnergyFact() {
        return energyFact;
    }

    public void setEnergyFact(double energyFact) {
        this.energyFact = energyFact;
    }

    public double getSmithoreFact() {
        return smithoreFact;
    }

    public void setSmithoreFact(double smithoreFact) {
        this.smithoreFact = smithoreFact;
    }


    @Override
    public String toString() {
        return ("Name: " + this.name + " " + "Race: " + this.race + " " + "Color: " + this.color);
    }

    @Override
    public boolean equals(Object other) {
        if (null == other) { return false; }
        if (this == other) { return true; }
        if (!(other instanceof Player)) { return false; }
        Player that = (Player) other;
        return this.name.equals(that.name) && this.race.equals(that.race) && this.color.equals(that.color);
    }
}
