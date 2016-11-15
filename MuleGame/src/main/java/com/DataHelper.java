package main.java.com;

import main.java.com.Models.HumanPlayer;
import main.java.com.Models.Mule;
import main.java.com.Models.Player;
import main.java.com.Models.Tile;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by uddhav on 11/3/15.
 */
public class DataHelper {

    public static void write(DataSaver f, File filename) throws Exception{
        PrintWriter in = new PrintWriter(filename);
        in.write(f.size + "\n");
        for (int i = 0; i < f.size; i++) {
            Player cur = f.players[i];

            in.write(cur.name + "," + cur.race + "," + cur.color + ","
                    + cur.getMoney() + ","
                    + cur.getCrystite() + "," + cur.getEnergy() + ","
                    + cur.getFood() + ","
                    + cur.getSmithore() + "," + cur.getAssets() + "\n");
        }

        in.write(f.playerLoopCounter + "\n");
        in.write(f.roundNum.get() + "\n");
        in.write(f.turnNum.get() + "\n");

        for (int i = 0; i < 45; i++) {
            Tile curr = f.tiles[i / 9][i % 9];
            in.write((curr.owner == null ? "NONE" : curr.owner.name)+ "," + (curr.mule == null ? "NONE" : curr.mule.getName()) + "\n");
        }
        in.close();
    }

    public static void read(File filename) throws Exception {

        Scanner scanner = new Scanner(filename);
        String[] playerStats;

        Data.size = scanner.nextInt();
        scanner.nextLine();
        //to account for newline char
        for (int i = 0; i < Data.size; i++) {
            Player current;

            System.out.println(Data.size);
            String line = scanner.nextLine();
            playerStats = line.split(",");
            System.out.println(playerStats[1]);
            current = new HumanPlayer("", "");
            current.name = playerStats[0];
            current.color = playerStats[2];
            current.race = playerStats[1];
            current.setAssets(Integer.parseInt(playerStats[8]));
            current.setSmithore(Integer.parseInt(playerStats[7]));
            current.setEnergy(Integer.parseInt(playerStats[5]));
            current.setFood(Integer.parseInt(playerStats[6]));
            current.setCrystite(Integer.parseInt(playerStats[4]));
            current.setMoney(Integer.parseInt(playerStats[3]));
            Data.players[i] = current;

        }

        Data.playerLoopCounter = scanner.nextInt();
        scanner.nextLine();
        Data.setRoundNum(scanner.nextInt());
        scanner.nextLine();
        Data.setTurnNum(scanner.nextInt());
        scanner.nextLine();

        for (int i = 0; i < 45; i++) {
            Tile curr = Data.tiles[i / 9][i % 9];
            //scanner.nextLine().split(",");
            String[] strarr;
            strarr = scanner.nextLine().split(",");

            if(!strarr[0].equals("NONE")) {
                System.out.println(strarr[0]);
                for (int j = 0; j < Data.size; j++) {
                    if (strarr[0].equalsIgnoreCase(Data.players[j].name)) {
                        curr.owner = Data.players[j];
                    }
                }

                if(!strarr[1].equals("NONE")) {
                    String str1 = strarr[1];
                    curr.mule = str1.equalsIgnoreCase("food") ? Mule.FOOD :
                            str1.equalsIgnoreCase("energy") ? Mule.ENERGY :
                            str1.equalsIgnoreCase("smithore") ? Mule.SMITHORE:
                                    Mule.CRYSTITE;
                }
            }
        }
        Data.roundOrder = new int[Data.size];
        for (int i = 0; i < Data.size; i++) {
            Data.roundOrder[i] = i;
        }

        scanner.close();
    }
}
