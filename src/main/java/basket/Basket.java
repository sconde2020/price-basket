package basket;

import discount.IDiscount;
import models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Basket implements IBasket {

    private final Map<String, Double> priceMap;
    private final Map<String, Integer> offerMap;

    private final List<Item> itemList;
    private final IDiscount discount;

    protected Basket(IDiscount discount, Map<String, Double> priceMap, Map<String, Integer> offerMap) {
        this.itemList = new ArrayList<>();
        this.discount = discount;
        this.priceMap = priceMap;
        this.offerMap = offerMap;
    }

    public void addItem(String itemName) {
        double itemPrice = this.priceMap.get(itemName);
        int itemOffer = this.offerMap.get(itemName) != null ? this.offerMap.get(itemName) : 0;
        Item item = new Item(itemName, itemPrice, itemOffer);
        this.itemList.add(item);
    }

    public double getSubTotal() {
        return this.itemList.stream()
                .map(Item::getPrice).reduce(Double::sum)
                .orElse(0.0);
    }

    public List<String> getOffers() {
        List<String> offerList = new ArrayList<>();

        this.offerMap.forEach((name, offer) -> {
            double itemDiscount =  this.discount.getItemDiscountInPennies(this.itemList, name);
            if (itemDiscount >= 100.0) {
                offerList.add(name + " " + offer + "% off:-£" + itemDiscount/100);
            } else if (itemDiscount != 0){
                offerList.add(name + " " + offer + "% off:-" + (int)itemDiscount + "p");
            }
        });

        if (offerList.isEmpty()) {
            offerList.add("(No offers available)");
        }

        return offerList;
    }

    public double getTotalPrice() {
        double subtotal = this.getSubTotal();
        double totalDiscount = this.discount.getTotalDiscount(this.itemList);
        return (subtotal - totalDiscount);
    }

}
