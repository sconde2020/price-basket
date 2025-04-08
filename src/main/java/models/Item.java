package models;

public class Item {
    private String name;
    private double price;
    private int offer;

    public Item(String name, double price, int offer) {
        this.name = name;
        this.price = price;
        this.offer = offer;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getOffer() {
        return offer;
    }
}
