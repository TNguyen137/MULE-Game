package main.java.com.Models;

/**
 * Created by uddhav on 11/8/15.
 */
public class BlacksmithPlayer extends Player {
    public BlacksmithPlayer(String name, String color) {
        super(name, "Blacksmith", color, 800, 8, 4);
        setFoodFact(.25);
        setEnergyFact(.25);
        setCrystiteFact(1.50);
        setSmithoreFact(1.50);
    }
}
