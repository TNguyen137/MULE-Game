package main.java.com.Models;

/**
 * Created by uddhav on 11/8/15.
 */
public class XergPlayer extends Player {
    public XergPlayer(String name, String color) {
        super(name, "Xerg", color, 1400, 0, 0);
        setFoodFact(1.25);
        setEnergyFact(1.25);
        setCrystiteFact(1.25);
        setSmithoreFact(1.25);
    }
}