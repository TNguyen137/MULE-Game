package main.java.com.Models;

/**
 * Created by lilTjeezy on 10/10/2015.
 *
 * Mule class to create a mule and use it for transportation
 * of resources
 */
public enum Mule {
    FOOD("food", 125, 2, 0, 0, 0, "/main/java/com/img/food.png")
        , ENERGY("energy", 175, 0, 3, 0, 0, "/main/java/com/img/energy.png")
        , SMITHORE("smithore", 150, 0, 0, 2, 0, "/main/java/com/img/ore.png")
        , CRYSTITE("crystite", 200, 0, 0, 0, 2, "/main/java/com/img/crystite.png")
        , MINING("mining", 450, 0, 0, 2, 2, "/main/java/com/img/crystite.png")
        , SUPERMULE("supermule", 1000, 2, 2, 2, 2, "/main/java/com/img/crystite.png")
        , SUSTENANCE("sustenance", 450, 2, 2, 0, 0, "/main/java/com/img/crystite.png");

    private final String name;
    private final int price;
    private final int foodProduction;
    private final int energyProduction;
    private final int smithoreProduction;
    private final int crystiteProduction;
    private final String location;

    public int getCrystiteProduction() {
        return crystiteProduction;
    }

    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public int getFoodProduction() {
        return foodProduction;
    }

    public int getEnergyProduction() {
        return energyProduction;
    }

    public int getSmithoreProduction() {
        return smithoreProduction;
    }

    Mule(String name, int price, int foodProduction
        , int energyProduction, int smithoreProduction
        , int crystiteProduction, String location) {
        this.name = name;
        this.price = price;
        this.foodProduction = foodProduction;
        this.energyProduction = energyProduction;
        this.smithoreProduction = smithoreProduction;
        this.crystiteProduction = crystiteProduction;
        this.location = location;
    }
}
