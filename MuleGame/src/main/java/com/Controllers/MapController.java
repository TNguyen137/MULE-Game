package main.java.com.Controllers;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.com.Data;
import main.java.com.DataHelper;
import main.java.com.DataSaver;
import main.java.com.Models.Mule;
import main.java.com.Models.Player;
import main.java.com.Models.Tile;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Random;

/**
 * Created by uddhav on 10/4/15.
 */

public class MapController {
    /**
     * Holder Parent Class.
     */
    Parent parent;
    /**
     * Holder Scene.
     */
    Scene scene;
    /**
     * Holder Stage.
     */
    Stage stage;

    //Map grid (contains the tiles)
    @FXML
    GridPane mapGrid;

    //Data Grid contains the player's information
    @FXML
    GridPane dataGrid;

    /**
     * Map Controller constructor.
     * Loads an object hierarchy from an XML document.
     * @throws IOException
     */
    public MapController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
            .getResource("../Views/map.fxml"));
        fxmlLoader.setController(this);
        parent = fxmlLoader.load();
        scene = new Scene(parent, 800, 600);
        initGame();
    }

    /**
     * Method initGame sets the informational grid of the player's display.
     * Also, this method sets the horizontal gap, vertical gap, and padding of each
     * image in the map.
     *
     *
     */
    public void initGame() {
        dataGrid.setHgap(40);
        dataGrid.setVgap(100);
        dataGrid.setPadding(new Insets(0, 10, 0, 10));

        int i;
        for (i = 0; i < Data.size; i++) {

            //Create a vertical box to store the player's information display
            VBox personData = new VBox();
            personData.setPadding(new Insets(20, 10, 20, 10));

            //Horizontal box to put the player's name
            HBox zero = new HBox();
            //Recall: need to add children of horizontal box, cannot add to horizontal box itself
            zero.getChildren().add(new Label("Person :"));
            zero.getChildren().add(new Label(Data.players[i].name));

            //Horizontal box to put the total (Money + Assets)
            HBox one = new HBox();
            Label labelone = new Label();
            one.getChildren().add(new Label("Total :"));
            //bind the text to changes. For instance, whenever change in totalproperty, then change in text also occurs
            //binding in this case in unidirectional
            labelone.textProperty().bind(Bindings.convert(Data.players[i].totalProperty()));
            one.getChildren().add(labelone);

            //Horizontal box to put the Money
            HBox oneone = new HBox();
            Label labeloneone = new Label();
            oneone.getChildren().add(new Label("Money :"));
            //same binding property as described above
            labeloneone.textProperty().bind(Bindings.convert(Data.players[i].moneyProperty()));
            oneone.getChildren().add(labeloneone);

            //Horizontal box to put the Assets
            HBox onetwo = new HBox();
            Label labelonetwo = new Label();
            onetwo.getChildren().add(new Label("Assets :"));
            labelonetwo.textProperty().bind(Bindings.convert(Data.players[i].assetsProperty()));
            onetwo.getChildren().add(labelonetwo);

            //Horizontal box to put the food quantity
            HBox two = new HBox();
            two.getChildren().add(new Label("Food :"));
            Label labeltwo = new Label();
            labeltwo.textProperty().bind(Bindings.convert(Data.players[i].foodProperty()));
            two.getChildren().add(labeltwo);

            //Horizontal box to put the energy quantity
            HBox three = new HBox();
            Label labelthree = new Label();
            labelthree.textProperty().bind(Bindings.convert(Data.players[i].energyProperty()));
            three.getChildren().add(new Label("Energy :"));
            three.getChildren().add(labelthree);

            //Horizontal box to put the crystite quantity
            HBox four = new HBox();
            Label labelfour = new Label();
            labelfour.textProperty().bind(Bindings.convert(Data.players[i].crystiteProperty()));
            four.getChildren().add(new Label("Crystite :"));
            four.getChildren().add(labelfour);

            //Horizontal box to put the smithore quantity
            HBox five = new HBox();
            Label labelfive = new Label();
            labelfive.textProperty().bind(Bindings.convert(Data.players[i].smithoreProperty()));
            five.getChildren().add(new Label("Smithore :"));
            five.getChildren().add(labelfive);

            //personData is the Vbox. We are adding the HBoxes into personData to create its structure
            personData.getChildren().addAll(zero, one, oneone, onetwo, two, three, four, five);
            dataGrid.add(personData, i, 0);
        }

        //This part of the code does the same as above, but it displays the round num and turn number
        VBox gameData = new VBox();

        //Horizontal box to put the Round Num quantity
        HBox four = new HBox();
        Label labelfour = new Label();
        labelfour.textProperty().bind(Bindings.convert(Data.roundNumProperty()));
        four.getChildren().add(new Label("Round Num :"));
        four.getChildren().add(labelfour);

        //Horizontal box to put the Turn Num quantity
        HBox five = new HBox();
        Label labelfive = new Label();
        labelfive.textProperty().bind(Bindings.convert(Data.turnNumProperty().add(1)));
        five.getChildren().add(new Label("Turn Number :"));
        five.getChildren().add(labelfive);

        HBox six = new HBox();
        Button saveGame = new Button();
        saveGame.setText("Save Game");
        saveGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.showSaveDialog(stage);
                DataSaver dataSaver = new DataSaver();
                dataSaver.getData();
                try {
                    DataHelper.write(dataSaver, fileChooser.showSaveDialog(stage));
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        });
        six.getChildren().add(saveGame);
        //add hboxes to gameData(VBox)
        gameData.getChildren().addAll(four, five, six);
        dataGrid.add(gameData, i, 0);

        //Makes the grid for the map with all the tiles
        //Creates the town once we have entered all the person's details (for loop for data.size is done)
        for (i = 0; i < Data.ROWS; i++) {
            for (int j = 0; j < Data.COLUMNS; j++) {
                mapGrid.add(Data.tilePanes[i][j], j, i);
                Tile curr = Data.tiles[i][j];
                if(curr.owner != null) {
                    Data.tilePanes[i][j].setStyle("-fx-border-color: " + curr.owner.color + "; -fx-border-width: 4px 4px 4px 4px");
                }
                if(curr.mule != null) {
                    AnchorPane currentPane = Data.tilePanes[i][j];
                    ImageView image = new ImageView(new Image(curr.mule.getLocation()));
                    currentPane.getChildren().add(image);
                }
            }
        }

    }

    /**
     * Method to freeland distribution at the beginning (1st 2 rounds)
     * @param stage to be called
     */
    public void callMapRound(Stage stage) {

        //Checks for the first two rounds
        if (Data.getRoundNum() <= 2) {
            freeLandDistribution(stage, 0);
        } else {
            //look at function below
            setMapRound(stage);
        }
    }

    /**
     * Method that calls endGame if the #of rounds reaches to be 13.
     * @param stage
     */
    public void setMapRound(Stage stage) {
        if(Data.getRoundNum() == 13) {
            endGame();
        }

        //look at function below
        initRound();
        callMapNextTurn(stage);
    }

    /**
     * Start Round method. Sets players in order of total property from
     */
    public void initRound() {
        //We start with turn number 0
        Data.setTurnNum(0);
        //For the size (num of players)
        for (int i = 0; i < Data.size; i++) {
            //Round order is set to default values (1,2,3,4)
            //since no player has advantage over the other at beginning
            Data.roundOrder[i] = i;
        }
        Random rand = new Random();
        Data.factor = 1;
        if (rand.nextBoolean()) {   //50% chance
            Data.factor = Data.diff == 3 ? 100 - rand.nextInt(80) : Data.diff == 2 ? 100 : rand.nextInt(70) + 1;
            Data.factor = Data.factor / 100.0;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Change in production for this round");
            alert.setContentText("Factor =" + Data.factor);
            alert.showAndWait();
        }
        //We start from 0, so we end up in size - 1
        for (int c = 0; c < (Data.size - 1); c++) {
            for (int d = 0; d < Data.size - c - 1; d++) {
                //Sort the player's turns (roundOrder = [0,1,2,3], check property of player from the roundOrder)
                //For instance, let d = 0, then roundOrder[0] = 0, players[0] = player 0
                //Set condition to see if next player has greater assets. If he does, then change order.
                if (Data.players[Data.roundOrder[d]].totalProperty().
                    greaterThan(Data.players[Data.roundOrder[d + 1]].
                            totalProperty()).get()) // For descending order use <
                {
                    //This is inside the if condition
                    int i = Data.roundOrder[d];
                    //Create var i to store the roundorder that we are going to swap
                    //Swap the round order (recall: descending order (higher to lower))
                    Data.roundOrder[d] = Data.roundOrder[d + 1];
                    Data.roundOrder[d + 1] = i;
                }
            }
        }
    }

    /**
     * Method that changes/calls map if some event while the turn is occurring happens
     *
     * @param stage to change to
     * @param reason it can be mule, land, etc
     */
    public void callMapWithinTurn(final Stage stage, String reason) {
        //Checks that timer is under the end time
        checkTurn();
        this.stage = stage;
        stage.setTitle("Welcome to Irata");
        //Change scene to map scene (stage changes scenes)
        stage.setScene(scene);
        stage.hide();
        stage.show();

        //If we return to the map for the following conditions:

        //Return to map because we have purchased a mule (click wisely to the tile you want to add the mule to)
        if (reason.equals("MULE")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            //Name of the mule is the type of the mule
            alert.setHeaderText("You have a " + Data.currentTurn.mule.getName() + " mule");
            alert.setContentText("Click wisely!");
            alert.showAndWait();

            //Create a mouse handler
            EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    int x = (int) mouseEvent.getSceneX() / 90;
                    int y = ((int) mouseEvent.getSceneY() - 185) / 65;
                    if (Data.currentTurn.mule == null) {
                        if (!(x == 4 && y == 2)) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Mule has already been used up or lost");
                            alert.setContentText("Go to shop");
                            alert.showAndWait();
                        }
                        try {
                            ShopController shopController = new ShopController();
                            shopController.callShop(stage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (x < 0 || y < 0 || x > 9 || y > 5 || (x == 4 && y == 2 && Data.currentTurn.mule != null)) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("warning");
                        alert.setHeaderText("Incorrect click!");
                        alert.setContentText("Try Again!");
                        alert.showAndWait();
                    } else {
                        //Get the tile and store it in current
                        Tile current = Data.tiles[y][x];

                        //Condition: if owner is the player of the round and mule is null (meaning there is no mule)
                        //then set mule of that tile to the bought mule
                        if (current.owner == Data.players[Data.roundOrder[Data.getTurnNum()]] && current.mule == null) {
                            current.mule = Data.currentTurn.mule;

                            //AnchorPane tiles(y,x)
                            AnchorPane currentPane = Data.tilePanes[y][x];
                            ImageView image = new ImageView(new Image(current.mule.getLocation()));
                            //Add image as a child
                            currentPane.getChildren().add(image);
                            //Current turn (Turn class) set to null after we have installed the image
                            Data.currentTurn.mule = null;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Info");
                            alert.setHeaderText("Mule has been installed");
                            alert.setContentText("Continue by going to the shop");
                            alert.showAndWait();
                        } else { // Stuff that can go wrong
                            Data.currentTurn.mule = null;
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Sorry, MULE Lost");

                            alert.setContentText("Continue, click on the town to proceed");

                            //If you do not own the tile or if there is a mule in the tile already
                            if (current.owner != Data.players[Data.roundOrder[Data.getTurnNum()]]) {
                                alert.setHeaderText("You didn't own the tile");
                            } else if (current.mule != null) {
                                alert.setHeaderText("There was a MULE already here");
                            }

                            alert.showAndWait();
                        }
                    }
                }
            };

            scene.setOnMouseClicked(mouseHandler);

            //If reason is Land
    } else if (reason.equals("buyLand")) { //If the player wants more land, add more conditions if necessary
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("BUY MORE LAND!");
        alert.setContentText("click on a tile to buy, otherwise click on the town to go back");
        alert.showAndWait();

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("SceneX : SceneY - " + mouseEvent.getSceneX() + " : " + mouseEvent.getSceneY());
                int x = (int) mouseEvent.getSceneX() / 90;
                int y = ((int) mouseEvent.getSceneY() - 185)/65;
                if (y < 5 * 65 && x < 9 * 90 && x > -1 && y > -1) {
                    System.out.println("Tile Reference = " + x + " , " + y);
                } else {
                    System.out.println("OOB");
                }
                Tile current = Data.tiles[y][x];
                if (x == 4 && y == 2) {
                    try {
                        ShopController shopController = new ShopController();
                        shopController.callShop(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (current.owner != null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("This tile is already owned");
                    alert.setContentText("Press okay to continue");
                    alert.showAndWait();
                } else {
                    //Formalities of buying a tile
                    Player currentPlayer = Data.players[Data.roundOrder[Data.getTurnNum()]];
                    current.owner = currentPlayer;
                    //Not free
                    if (currentPlayer.getMoney() > current.cost) {
                        currentPlayer.setMoney(currentPlayer.getMoney() - current.cost);
                        currentPlayer.setAssets(currentPlayer.getAssets() + current.cost / 2);
                        //this line is to add stuff to the tile
                        Data.tilePanes[y][x].setStyle("-fx-border-color: " + currentPlayer.color + "; -fx-border-width: 4px 4px 4px 4px");
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Balance Issue");
                        alert2.setHeaderText("You have not enough money to purchase this land");
                        alert2.setContentText("Press okay to continue");
                        alert2.showAndWait();
                    }
                    //currentPlayer.setMoney(currentPlayer.getMoney() - current.cost);
                    //currentPlayer.setAssets(currentPlayer.getAssets() + current.cost/2);
                    Data.tilePanes[y][x].setStyle("-fx-border-color: " + currentPlayer.color + "; -fx-border-width: 4px 4px 4px 4px");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Land Bought");
                    alert.setHeaderText("Land Bought");
                    alert.setContentText("Press okay to continue");
                    alert.showAndWait();
                    try {
                        ShopController shopController = new ShopController();
                        shopController.callShop(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        scene.setOnMouseClicked(mouseHandler);

        } else {
            EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    int x = (int) mouseEvent.getSceneX() / 90;
                    int y = ((int) mouseEvent.getSceneY() - 185) / 65;
                    if (x == 4 && y == 2) {
                        try {
                            ShopController shopController = new ShopController();
                            shopController.callShop(stage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            scene.setOnMouseClicked(mouseHandler);
        }
    }

    public void endGame() {
        //TODO
        System.out.println("We Won!");
    }


    /**
     * Calls map when everybody has made their move
     * @param stage to be changed to
     */
    public void callMapNextTurn(Stage stage){
        if (Data.getTurnNum() == Data.size) {
            //Signifies the end of a round.
            Data.setRoundNum(Data.getRoundNum() + 1);
            Data.setTurnNum(0);
            //Calls map round method that is in charge of free and land distribution. If not it goes to setMapRound()
            //SetMapRound() calls initRound()
            callMapRound(stage);

        } else {
            this.stage = stage;
            stage.setTitle("Welcome to Irata");
            stage.setScene(scene);
            stage.hide();
            stage.show();
            int time;
            Player currentPlayer = Data.players[Data.roundOrder[Data.getTurnNum()]];


            // Add all the resources for current player here
            // At the beginning of the turn

            int energyRequirement = 0;
            int foodProduction = 0;
            int energyProduction = 0;
            int smithoreProduction = 0;
            int crystiteProduction = 0;


            for(int i = 0; i < Data.ROWS; i++) {
                for(int j = 0; j < Data.COLUMNS; j++) {
                    Tile currentTile = Data.tiles[i][j];
                    if (currentPlayer.equals(currentTile.owner)) {
                        Mule currentMule = currentTile.mule;
                        if (currentMule != null) {
                            energyRequirement++;
                            foodProduction += currentMule.getFoodProduction() * currentPlayer.getFoodFact() * currentTile.getFoodFactor() * Data.factor;
                            energyProduction += currentMule.getEnergyProduction() * currentPlayer.getEnergyFact() * currentTile.getEnergyFactor() * Data.factor;
                            smithoreProduction += currentMule.getSmithoreProduction() * currentPlayer.getSmithoreFact() * currentTile.getSmithoreFactor() * Data.factor;;
                            crystiteProduction += currentMule.getCrystiteProduction() * currentPlayer.getCrystiteFact() * currentTile.getCrystiteFactor() * Data.factor;;
                        }
                    }
                }
            }

            //Cases for the amount of energy, food, smithore, and crystite
            if (currentPlayer.getEnergy() >= energyRequirement) {
                currentPlayer.setFood(Math.max(0, currentPlayer.getFood() + foodProduction - energyRequirement));
                currentPlayer.setEnergy(currentPlayer.getEnergy() + energyProduction - energyRequirement);
                currentPlayer.setCrystite(currentPlayer.getCrystite() + crystiteProduction);
                currentPlayer.setSmithore(currentPlayer.getSmithore() + smithoreProduction);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Production Stats");
                alert.setHeaderText("Number of Mules:" + energyRequirement);
                alert.setContentText("Food: " + foodProduction + "\nEnergy: "
                        + energyProduction + "\nSmithore: "
                        + smithoreProduction+ "\nCrystite: " + crystiteProduction);
                alert.showAndWait();
            } else {
                currentPlayer.setFood(Math.max(0, currentPlayer.getFood() + foodProduction - energyRequirement));
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Not Enough Energy");
                alert.setHeaderText("No Production Happened");
                alert.setContentText("Press okay to continue");
                alert.showAndWait();
            }

            //Conditions if lack of resources. Time is affected by these.
            if (currentPlayer.getFood() <= 4) {
                time = 40;
            } else if (currentPlayer.getFood() >= 6) {
                time = 60;
            } else {
                time = 50;
            }

            //if round is bigger than 2
            if (Data.getRoundNum() >= 3 && Data.size != 1) {
                Random random = new Random();
                int i = random.nextInt(4);
                switch (i) {
                    case 1:
                        //money event
                        //good
                        if (Data.getTurnNum() == 0) {
                            int increase = (random.nextInt(25) * Data.players[Data.roundOrder[Data.getTurnNum()]].getMoney())/100 + 100;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Good Random event has occurred!");
                            alert.setHeaderText("Player affected by random event: " + Data.players[Data.roundOrder[Data.getTurnNum()]].name);
                            alert.setContentText("Money has increased by: " + increase);
                            alert.showAndWait();
                            Data.players[Data.roundOrder[Data.getTurnNum()]].setMoney(Data.players[Data.roundOrder[Data.getTurnNum()]].getMoney() + increase);
                        }
                        //bad
                        if (Data.getTurnNum() == Data.size - 1) {
                            int increase = (random.nextInt(25) * Data.players[Data.roundOrder[Data.getTurnNum()]].getMoney())/100;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Bad Random event has occurred!");
                            alert.setHeaderText("Player affected by random event: " + Data.players[Data.roundOrder[Data.getTurnNum()]].name);
                            alert.setContentText("Money has decreased by: " + increase);
                            alert.showAndWait();
                            Data.players[Data.roundOrder[Data.getTurnNum()]].setMoney(Data.players[Data.roundOrder[Data.getTurnNum()]].getMoney() - increase);
                        }
                        break;
                    case 2: //food
                        if (Data.getTurnNum() == 0) {
                            int increase = (random.nextInt(25) * Data.players[Data.roundOrder[Data.getTurnNum()]].getFood())/100 + 1;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Good Random event has occurred!");
                            alert.setHeaderText("Player affected by random event: " + Data.players[Data.roundOrder[Data.getTurnNum()]].name);
                            alert.setContentText("Food has increased by: " + increase);
                            alert.showAndWait();
                            Data.players[Data.roundOrder[Data.getTurnNum()]].setFood(Data.players[Data.roundOrder[Data.getTurnNum()]].getFood() + increase);
                        }
                        //bad
                        if (Data.getTurnNum() == Data.size - 1) {
                            int increase = (random.nextInt(25) * Data.players[Data.roundOrder[Data.getTurnNum()]].getFood())/100;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Bad Random event has occurred!");
                            alert.setHeaderText("Player affected by random event: " + Data.players[Data.roundOrder[Data.getTurnNum()]].name);
                            alert.setContentText("Food has decreased by: " + increase);
                            alert.showAndWait();
                            Data.players[Data.roundOrder[Data.getTurnNum()]].setFood(Data.players[Data.roundOrder[Data.getTurnNum()]].getFood() - increase);
                        }
                        break;

                    case 3: //energy
                        if (Data.getTurnNum() == 0) {
                            int increase = (random.nextInt(25) * Data.players[Data.roundOrder[Data.getTurnNum()]].getEnergy())/100 + 1;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Good Random event has occurred!");
                            alert.setHeaderText("Player affected by random event: " + Data.players[Data.roundOrder[Data.getTurnNum()]].name);
                            alert.setContentText("Energy has increased by: " + increase);
                            alert.showAndWait();
                            Data.players[Data.roundOrder[Data.getTurnNum()]].setEnergy(Data.players[Data.roundOrder[Data.getTurnNum()]].getEnergy() + increase);
                        }
                        //bad
                        if (Data.getTurnNum() == Data.size - 1) {
                            int increase = (random.nextInt(25) * Data.players[Data.roundOrder[Data.getTurnNum()]].getEnergy())/100;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Bad Random event has occurred!");
                            alert.setHeaderText("Player affected by random event: " + Data.players[Data.roundOrder[Data.getTurnNum()]].name);
                            alert.setContentText("Energy has decreased by: " + increase);
                            alert.showAndWait();
                            Data.players[Data.roundOrder[Data.getTurnNum()]].setEnergy(Data.players[Data.roundOrder[Data.getTurnNum()]].getEnergy() - increase);
                        }
                        break;

                    case 4: //smithore
                        if (Data.getTurnNum() == 0) {
                            int increase = (random.nextInt(25) * Data.players[Data.roundOrder[Data.getTurnNum()]].getSmithore()) / 100 + 1;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Good Random event has occurred!");
                            alert.setHeaderText("Player affected by random event: " + Data.players[Data.roundOrder[Data.getTurnNum()]].name);
                            alert.setContentText("Smithore has increased by: " + increase);
                            alert.showAndWait();
                            Data.players[Data.roundOrder[Data.getTurnNum()]].setSmithore(Data.players[Data.roundOrder[Data.getTurnNum()]].getSmithore() + increase);
                            //bad
                        }
                            if (Data.getTurnNum() == Data.size - 1) {
                                int increase = (random.nextInt(25) * Data.players[Data.roundOrder[Data.getTurnNum()]].getSmithore()) / 100;
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Bad Random event has occurred!");
                                alert.setHeaderText("Player affected by random event: " + Data.players[Data.roundOrder[Data.getTurnNum()]].name);
                                alert.setContentText("Smithore has decreased by: " + increase);
                                alert.showAndWait();
                                Data.players[Data.roundOrder[Data.getTurnNum()]].setSmithore(Data.players[Data.roundOrder[Data.getTurnNum()]].getSmithore() - increase);
                            }
                            //smithore
                            break;

                    case 0: //crystite
                        if (Data.getTurnNum() == 0) {
                            int increase = (random.nextInt(25) * Data.players[Data.roundOrder[Data.getTurnNum()]].getCrystite())/100 + 1;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Good Random event has occurred!");
                            alert.setHeaderText("Player affected by random event: " + Data.players[Data.roundOrder[Data.getTurnNum()]].name);
                            alert.setContentText("Crystite has increased by: " + increase);
                            alert.showAndWait();
                            Data.players[Data.roundOrder[Data.getTurnNum()]].setCrystite(Data.players[Data.roundOrder[Data.getTurnNum()]].getCrystite() + increase);
                        }
                        //bad
                        if (Data.getTurnNum() == Data.size - 1) {
                            int increase = (random.nextInt(25) * Data.players[Data.roundOrder[Data.getTurnNum()]].getCrystite())/100;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Bad Random event has occurred!");
                            alert.setHeaderText("Player affected by random event: " + Data.players[Data.roundOrder[Data.getTurnNum()]].name);
                            alert.setContentText("Crystite has decreased by: " + increase);
                            alert.showAndWait();
                            Data.players[Data.roundOrder[Data.getTurnNum()]].setCrystite(Data.players[Data.roundOrder[Data.getTurnNum()]].getCrystite() - increase);
                        }
                        //crystite
                        break;
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Begin your turn.");
            alert.setHeaderText("Current Player: " + Data.players[Data.roundOrder[Data.getTurnNum()]].name);
            alert.setContentText("Time in seconds: " + time);
            alert.showAndWait();

            Data.currentTurn.endTime = LocalTime.now().plusSeconds(time);
            Data.currentTurn.mule = null;
            final Stage stageCopy = stage;

            EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    int x = (int) mouseEvent.getSceneX() / 90;
                    int y = ((int) mouseEvent.getSceneY() - 185) / 65;
                    if (x == 4 && y == 2) {
                        try {
                            ShopController shopController = new ShopController();
                            shopController.callShop(stageCopy);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            scene.setOnMouseClicked(mouseHandler);
        }
    }

    /**
     * Method that controls free land distribution. Land is given to the players
     *
     * @param stage stage
     * @param number to compare with size
     */
    public void freeLandDistribution(Stage stage, int number) {

        if (number == Data.size) {
            initRound();
            callMapNextTurn(stage);
        } else {

            this.stage = stage;
            stage.setTitle("Welcome to Irata");
            stage.setScene(scene);
            stage.hide();
            stage.show();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Player Name: " + Data.players[number].name);
            alert.setContentText("Press okay to get free land");
            alert.showAndWait();

            final int numCopy = number;
            final Stage stageCopy = stage;

            EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("SceneX : SceneY - " + mouseEvent.getSceneX() + " : " + mouseEvent.getSceneY());
                    int x = (int) mouseEvent.getSceneX() / 90;
                    int y = ((int) mouseEvent.getSceneY() - 185)/65;
                    if (y < 5 * 65 && x < 9 * 90 && x > -1 && y > -1) {
                        System.out.println("Tile Reference = " + x + " , " + y);
                    } else {
                        System.out.println("OOB");
                    }



                    //Same conditions
                    Tile current = Data.tiles[y][x];
                    if (x == 4 && y == 2) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("This tile is the town tile");
                        alert.setContentText("Press okay to continue");
                        alert.showAndWait();
                    } else if (current.owner != null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("This tile is already owned");
                        alert.setContentText("Press okay to continue");
                        alert.showAndWait();
                    } else {
                        //Formalities of buying a tile
                        Player currentPlayer = Data.players[numCopy];
                        current.owner = currentPlayer;
                        //JK, it is free
                        if (currentPlayer.getMoney() > current.cost) {
                            //currentPlayer.setMoney(currentPlayer.getMoney() - current.cost);
                            currentPlayer.setAssets(currentPlayer.getAssets() + current.cost / 2);
                            //this line is to add stuff to the tile
                            Data.tilePanes[y][x].setStyle("-fx-border-color: " + currentPlayer.color + "; -fx-border-width: 4px 4px 4px 4px");
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Balance Issue");
                            alert2.setHeaderText("You have not enough money to purchase this land");
                            alert2.setContentText("Press okay to continue");
                            alert2.showAndWait();
                        }
                        //currentPlayer.setMoney(currentPlayer.getMoney() - current.cost);
                        //currentPlayer.setAssets(currentPlayer.getAssets() + current.cost/2);
                        Data.tilePanes[y][x].setStyle("-fx-border-color: " + currentPlayer.color + "; -fx-border-width: 4px 4px 4px 4px");
                        freeLandDistribution(stageCopy, numCopy + 1);
                    }
                }
            };
            scene.setOnMouseClicked(mouseHandler);
        }
    }

    //public void handleSell

    /**
     * Checks turn to see if it has passed the limiting time
     */
    public void checkTurn() {
        if (LocalTime.now().isAfter(Data.currentTurn.endTime)) {
            try {
                ShopController shopController = new ShopController();
                shopController.endTurn(stage, "timed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
