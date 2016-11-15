package main.java.com.Controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.java.com.Data;
import main.java.com.Models.Mule;
import main.java.com.Models.Player;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by uddhav on 10/4/15.
 * Controller used at the shop. Class being used when we are in
 * the shop.
 */

public class ShopController {

    /**
     * Sidenote:
     *
     * 1. Parent class (extends Node class) handles all
     * hierarchical scene graph operations,
     * including adding/removing child nodes,
     * marking branches dirty for layout and rendering, picking,
     * bounds calculations, and executing the layout pass on each pulse.
     *
     * 2. The JavaFX Scene class is the container for all
     * content in a scene graph.
     * The background of the scene is filled as
     * specified by the fill property.
     * The application must specify the root Node for
     * the scene graph by setting the root property.
     *
     * 3. The JavaFX Stage class is the top level JavaFX container.
     * The primary Stage is constructed by the platform.
     * Additional Stage objects may be constructed by the application.
     * Stage objects must be constructed and modified
     * on the JavaFX Application Thread.
     */
    /**
     * Holder Parent.
     */
    private Parent parent;
    /**
     * Holder Scene.
     */
    private Scene scene;
    /**
     * Holder stage.
     */
    private Stage stage;

    /**
     * Constructor for the shop. Changes location to shop fxml.
     * Sets the controller to be in the shop.
     * Loads an object hierarchy from an XML document.
     * @throws IOException changing screens
     */
    public ShopController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("../Views/Shop.fxml"));
        fxmlLoader.setController(this);
        parent = fxmlLoader.load();
        scene = new Scene(parent, 800, 600);
    }

    /**
     * Calls the shop fxml and changes the stage to be the shop.
     * Ends turn if the turn passes the minute
     * @param stage to be changed to (shop)
     * @throws IOException changing screens
     * @throws NoSuchElementException tbd
     */
    public final void callShop(final Stage stage) throws IOException,
        NoSuchElementException {
        this.stage = stage;
        stage.setTitle("Welcome to Irata's Shop");
        stage.setScene(scene);
        stage.hide();
        stage.show();

        //Ends turn if the time passes the endTime (set to be a minute)
        if (LocalTime.now().isAfter(Data.currentTurn.endTime)) {
            endTurn(stage, "time");
        }

        //Initialize the mouse event (event caused by the touch of the mouse)
        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent mouseEvent) {
                System.out.println("SceneX : SceneY - "
                    + mouseEvent.getSceneX() + " : " + mouseEvent.getSceneY());
                int x = (int) mouseEvent.getSceneX();
                int y = (int) mouseEvent.getSceneY();

                /**
                 * To sell crystite (numbers are the boundaries the anchorpane)
                 * in order to click the right position & be charged/deposited.
                 * Same goes for energy, smithore, food
                 */
                try {
                    // To Trade Crystite
                    if (x > 3 && x < 190 && y > 9 && y < 274) {
                        tradeCrystite();
                    } else if (x > 198 && x < 380 && y > 7 && y < 274) {
                        tradeEnergy();
                    } else if (x > 420 && x < 600 && y > 9 && y < 275) {
                        tradeSmithore();
                    } else if (x > 612 && x < 800 && y > 9 && y < 275) {
                        tradeFood();
                    } else if (x > 610 && x < 800 && y > 330 && y < 591) {
                        buyMule(stage);
                    } else if (x > 420 && x < 600 && y > 330 && y < 591) {
                        endTurn(stage, "pub");
                    } else if (x > 198 && x < 380 && y > 330 && y < 591) {
                        MapController mapController = new MapController();
                        mapController.callMapWithinTurn(stage, "buyLand");
                    } else {
                        MapController mapController = new MapController();
                        mapController.callMapWithinTurn(stage, "chill");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        scene.setOnMouseClicked(mouseHandler);
    }
    /**
     * End Turn method that takes into consideration the possible cases
     * that can occur to have a turn be ended.
     * @param stage to be set once the turn ends
     * @param source type of source, since turn
     * can end depending on different sources.
     * @throws IOException for changing screens
     */
    public final void endTurn(final Stage stage, final String source)
        throws IOException {
        if (source.equals("pub")) {
            int num = 60 * (LocalTime.now().getMinute()
                    - Data.currentTurn.endTime.getMinute())
                    + (LocalTime.now().getSecond()
                    - Data.currentTurn.endTime.getSecond());
            int add = (int) (2.5 * (num));
            Player current = Data.players[Data.roundOrder[Data.getTurnNum()]];
            current.setMoney(current.getMoney() - add);

        }   else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Turn has ended because time got over");
            alert.setContentText("Press okay to continue");
            alert.showAndWait();
        }

        //Update other resources

        Data.setTurnNum(Data.getTurnNum() + 1);
        MapController mapController = new MapController();
        mapController.callMapNextTurn(stage);
    }


    // These methods are for trading different goods

    public void tradeFood() {
        int foodPrice = 25;
        List<String> transactionChoices = new ArrayList<>();
        transactionChoices.add("BUY");
        transactionChoices.add("SELL");

        Player current = Data.players[Data.roundOrder[Data.getTurnNum()]];

        ChoiceDialog<String> transactionDialog = new ChoiceDialog<>("Type", transactionChoices);
        transactionDialog.setTitle("Transaction");
        transactionDialog.setHeaderText("Please choose the type of transaction desired");
        transactionDialog.setContentText("Select Transaction");
        Optional<String> transactionAnswer = transactionDialog.showAndWait();

        if (transactionAnswer.isPresent()) {
            if (transactionAnswer.get().equalsIgnoreCase("buy")) {
                ChoiceDialog<String> dialog = new ChoiceDialog<>("Type", generateList(10));
                dialog.setTitle("Food Quantity Selection");
                dialog.setHeaderText("Please choose the quantity of food you want to purchase.");
                dialog.setContentText("Quantity: ");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    try {
                        tradeFoodHelper("buy", Integer.parseInt(result.get()), current);
                    } catch (IllegalArgumentException e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Not enough money to buy food");
                        alert.setContentText("Press okay to continue");
                        alert.showAndWait();
                    }
                }
            } else {
                ArrayList<String> foodChoices = generateList(10);
                ChoiceDialog<String> dialog = new ChoiceDialog<>("Select One", foodChoices);
                dialog.setTitle("Food Quantity Selection");
                dialog.setHeaderText("Please choose the quantity of food you want to sell. " +
                        "Quantity is based on the amount of food you have");
                dialog.setContentText("Quantity: ");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    try {
                        tradeFoodHelper("sell", Integer.parseInt(result.get()), current);
                    } catch (IllegalArgumentException e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Not enough food to sell");
                        alert.setContentText("Press okay to continue");
                        alert.showAndWait();
                    }
                }
            }
        }
    }


    public static void tradeFoodHelper(String type, int quantity, Player player)
            throws IllegalArgumentException {
        int foodPrice = 25;
        int current = player.getFood();
        int money = player.getMoney();
        if (type.equalsIgnoreCase("sell") && quantity > current) {
            throw new IllegalArgumentException("Not Enough Food to sell");
        } else if (type.equalsIgnoreCase("buy") && money < foodPrice * quantity) {
            throw new IllegalArgumentException("Not Enough Money to buy");
        } else if (type.equalsIgnoreCase("buy")) {
            player.setFood(current + quantity);
            player.setMoney(money  - foodPrice * quantity);
        } else {
            player.setFood(current - quantity);
            player.setMoney(money  + foodPrice * quantity);
        }
    }
    public void tradeEnergy() {
        int energyPrice = 75;
        List<String> transactionChoices = new ArrayList<>();
        transactionChoices.add("BUY");
        transactionChoices.add("SELL");

        Player current = Data.players[Data.roundOrder[Data.getTurnNum()]];

        ChoiceDialog<String> transactionDialog = new ChoiceDialog<>("Type", transactionChoices);
        transactionDialog.setTitle("Transaction");
        transactionDialog.setHeaderText("Please choose the type of transaction desired");
        transactionDialog.setContentText("Select Transaction");
        Optional<String> transactionAnswer = transactionDialog.showAndWait();

        if (transactionAnswer.isPresent()) {
            if (transactionAnswer.get().equalsIgnoreCase("buy")) {
                ChoiceDialog<String> dialog = new ChoiceDialog<>("Type", generateList(10));
                dialog.setTitle("Energy Quantity Selection");
                dialog.setHeaderText("Please choose the quantity of energy you want to purchase.");
                dialog.setContentText("Quantity: ");

                Optional<String> result = dialog.showAndWait();
                int cost = Integer.parseInt(result.get()) * energyPrice;
                if (result.isPresent()) {
                    if (current.getMoney() >= cost) {
                        System.out.println(current.getMoney());
                        current.setMoney(current.getMoney() - cost);
                        current.setEnergy(current.getEnergy() + Integer.parseInt(result.get()));
                        System.out.println("Your choice: " + result.get());
                        System.out.println("Current Money is: " + current.getMoney());
                    } else {
                        System.out.println("Not enough Money to buy!");
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("You do not have enough Money to buy.");
                        alert.setContentText("Press okay to continue");
                        alert.showAndWait();
                    }
                }
            } else {
                ArrayList<String> energyChoices = generateList(current.getEnergy());
                ChoiceDialog<String> dialog = new ChoiceDialog<>("Select One", energyChoices);
                dialog.setTitle("Energy Quantity Selection");
                dialog.setHeaderText("Please choose the quantity of energy you want to sell. " +
                        "Quantity is based on the amount of energy you have");
                dialog.setContentText("Quantity: ");

                Optional<String> result = dialog.showAndWait();
                int profit = Integer.parseInt(result.get()) * energyPrice;
                if (result.isPresent()) {
                    current.setMoney(current.getMoney() + profit);
                    current.setEnergy(current.getEnergy() - Integer.parseInt(result.get()));
                }
            }
        }
    }

    public void tradeCrystite() {
        int crystitePrice = 100;
        List<String> transactionChoices = new ArrayList<>();
        transactionChoices.add("BUY");
        transactionChoices.add("SELL");

        Player current = Data.players[Data.roundOrder[Data.getTurnNum()]];

        ChoiceDialog<String> transactionDialog = new ChoiceDialog<>("Type", transactionChoices);
        transactionDialog.setTitle("Transaction");
        transactionDialog.setHeaderText("Please choose the type of transaction desired");
        transactionDialog.setContentText("Select Transaction");
        Optional<String> transactionAnswer = transactionDialog.showAndWait();

        if (transactionAnswer.isPresent()) {
            if (transactionAnswer.get().equalsIgnoreCase("buy")) {
                ChoiceDialog<String> dialog = new ChoiceDialog<>("Type", generateList(10));
                dialog.setTitle("Crystite Quantity Selection");
                dialog.setHeaderText("Please choose the quantity of crystite you want to purchase.");
                dialog.setContentText("Quantity: ");

                Optional<String> result = dialog.showAndWait();
                int cost = Integer.parseInt(result.get()) * crystitePrice;
                if (result.isPresent()) {
                    if (current.getMoney() >= cost) {
                        System.out.println(current.getMoney());
                        current.setMoney(current.getMoney() - cost);
                        current.setCrystite(current.getCrystite() + Integer.parseInt(result.get()));
                        System.out.println("Your choice: " + result.get());
                        System.out.println("Current Money is: " + current.getMoney());
                    } else {
                        System.out.println("Not enough Money to buy!");
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("You do not have enough Money to buy.");
                        alert.setContentText("Press okay to continue");
                        alert.showAndWait();
                    }
                }
            } else {
                ArrayList<String> crystiteChoices = generateList(current.getCrystite());
                ChoiceDialog<String> dialog = new ChoiceDialog<>("Select One", crystiteChoices);
                dialog.setTitle("Crystite Quantity Selection");
                dialog.setHeaderText("Please choose the quantity of crystite you want to sell. " +
                        "Quantity is based on the amount of crystite you have");
                dialog.setContentText("Quantity: ");

                Optional<String> result = dialog.showAndWait();
                int profit = Integer.parseInt(result.get()) * crystitePrice;
                if (result.isPresent()) {
                    current.setMoney(current.getMoney() + profit);
                    current.setCrystite(current.getCrystite() - Integer.parseInt(result.get()));
                }
            }
        }
    }

    public void tradeSmithore() {
        int smithorePrice = 50;
        List<String> transactionChoices = new ArrayList<>();
        transactionChoices.add("BUY");
        transactionChoices.add("SELL");

        Player current = Data.players[Data.roundOrder[Data.getTurnNum()]];

        ChoiceDialog<String> transactionDialog = new ChoiceDialog<>("Type", transactionChoices);
        transactionDialog.setTitle("Transaction");
        transactionDialog.setHeaderText("Please choose the type of transaction desired");
        transactionDialog.setContentText("Select Transaction");
        Optional<String> transactionAnswer = transactionDialog.showAndWait();

        if (transactionAnswer.isPresent()) {
            if (transactionAnswer.get().equalsIgnoreCase("buy")) {
                ChoiceDialog<String> dialog = new ChoiceDialog<>("Type", generateList(10));
                dialog.setTitle("Smithore Quantity Selection");
                dialog.setHeaderText("Please choose the quantity of smithore you want to purchase.");
                dialog.setContentText("Quantity: ");

                Optional<String> result = dialog.showAndWait();
                int cost = Integer.parseInt(result.get()) * smithorePrice;
                if (result.isPresent()) {
                    if (current.getMoney() >= cost) {
                        System.out.println(current.getMoney());
                        current.setMoney(current.getMoney() - cost);
                        current.setSmithore(current.getSmithore() + Integer.parseInt(result.get()));
                        System.out.println("Your choice: " + result.get());
                        System.out.println("Current Money is: " + current.getMoney());
                    } else {
                        System.out.println("Not enough Money to buy!");
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("You do not have enough Money to buy.");
                        alert.setContentText("Press okay to continue");
                        alert.showAndWait();
                    }
                }
            } else {
                ArrayList<String> smithoreChoices = generateList(current.getSmithore());
                ChoiceDialog<String> dialog = new ChoiceDialog<>("Select One", smithoreChoices);
                dialog.setTitle("Smithore Quantity Selection");
                dialog.setHeaderText("Please choose the quantity of smithore you want to sell. " +
                        "Quantity is based on the amount of smithore you have");
                dialog.setContentText("Quantity: ");

                Optional<String> result = dialog.showAndWait();
                int profit = Integer.parseInt(result.get()) * smithorePrice;
                if (result.isPresent()) {
                    current.setMoney(current.getMoney() + profit);
                    current.setSmithore(current.getSmithore() - Integer.parseInt(result.get()));
                }
            }
        }
    }

    private ArrayList<String> generateList(int num) {
        ArrayList<String> choices = new ArrayList<>();

        if (num <= 0) {
            choices.add(String.valueOf(0));
            return choices;
        }


        for (int i = 0; i <= num; i++) {
            choices.add(String.valueOf(i));
        }

        return choices;
    }

    // To buy and establish mules
    public void buyMule(Stage stage) throws IOException {

        List<String> choices = new ArrayList<>();
        choices.add("Energy");
        choices.add("Food");
        choices.add("Smithore");
        choices.add("Crystite");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Type", choices);
        dialog.setTitle("Mule Selection");
        dialog.setHeaderText("Please choose the type of Mule to buy.");
        dialog.setContentText("Choose your type:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String type = result.get();
            Mule currentMule = type.equals("Energy") ?  Mule.ENERGY :
                (type.equals("Food") ?  Mule.FOOD : type.equals("Smithore") ?  Mule.SMITHORE : Mule.CRYSTITE);
            Player current = Data.players[Data.roundOrder[Data.getTurnNum()]];
            if (current.getMoney() >= currentMule.getPrice()) {
                current.setMoney(current.getMoney() - currentMule.getPrice());
                Data.currentTurn.mule = currentMule;
                MapController mapController = new MapController();
                mapController.callMapWithinTurn(stage, "MULE");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Not enough money to buy the current MULE");
                alert.setContentText("Press okay to continue");
                alert.showAndWait();
            }
        }
    }

}
