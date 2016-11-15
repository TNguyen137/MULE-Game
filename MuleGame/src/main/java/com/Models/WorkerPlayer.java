package main.java.com.Models;

/**
 * Created by uddhav on 11/8/15.
 */
public class WorkerPlayer extends Player {
    public WorkerPlayer(String name, String color) {
        super(name, "Worker", color, 1200, 6, 3);
        setFoodFact(2.0);
        setEnergyFact(2.0);
        setCrystiteFact(0.6);
        setSmithoreFact(0.6);
    }
}