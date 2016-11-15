package main.java.com.Models;

/**
 * Created by uddhav on 11/8/15.
 */
public class ZenomorphPlayer extends Player {
    public ZenomorphPlayer(String name, String color) {
        super(name, "Zenomorph", color, 3200, 0, 0);
        setFoodFact(0.6);
        setEnergyFact(0.6);
        setCrystiteFact(0.6);
        setSmithoreFact(0.6);
    }
}
