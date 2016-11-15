package main.java.com.Models;

import java.time.LocalTime;

/**
 * Created by uddhav on 10/4/15.
 */

/**
 * Class to keep track of the turn
 */
public class Turn {

    /**
     * Initial conditions of the turn
     * Player num = -1 (at the beginning)
     * EndTime = time needed to end the turn and change to other player
     */
    public int playerNum = -1;
    public LocalTime endTime;
    public Mule mule = null;
    //TODO Need to change this to produce encapsulation (change to private and create respective methods)

    /**
     * Resets the turn to be at the beginning again
     */
    public void reset() {
        playerNum = -1;
    }

    /**
     * Set player number
     * @param playerNum = player number
     */
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
}
