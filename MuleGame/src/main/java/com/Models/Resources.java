package main.java.com.Models;

/**
 * Created by ibanar on 10/3/15.
 */
public class Resources {
    private int smithore;
    private int food;
    private int crystite;
    private int energy;


    public Resources(int smithore, int food, int crystite, int energy) {
        this.smithore = smithore;
        this.food = food;
        this.crystite = crystite;
        this.energy = energy;
    }

    public int getSmithore() {
        return smithore;
    }

    public void setSmithore(int smithore) {
        this.smithore = smithore;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getCrystite() {
        return crystite;
    }

    public void setCrystite(int crystite) {
        this.crystite = crystite;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public boolean hasSmithore() {
        if (getSmithore() <= 0) {
            return false;
        }
        return false;
    }

    public boolean hasFood() {
        if (getFood() <= 0) {
            return false;
        }
        return true;
    }

    public boolean hasCrystite() {
        if (getCrystite() <= 0) {
            return false;
        }
        return true;
    }

    public boolean hasEnergy() {
        if (getEnergy() <= 0) {
            return false;
        }
        return true;
    }

}
