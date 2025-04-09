package services;

import models.Basket;
import models.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BasketService implements IBasketService {
    private final IDiscountService discountService;
    private final Map<String, Double> priceMap;
    private final Map<String, Integer> offerMap;

    public BasketService(IDiscountService discountService) {
        this.discountService = discountService;
        this.priceMap = discountService.priceMap();
        this.offerMap = discountService.offerMap();
    }

    public boolean isValidBasket(String[] itemNames) {
        // Check that every item name in the input exists in the price map (i.e., is a recognized product)
        return Arrays.stream(itemNames)
                .allMatch(this.priceMap::containsKey);
    }

    public void addItem(String itemName, Basket basket) {
        // Get item price from price list
        double itemPrice = this.priceMap.get(itemName);

        // Get item offer if it exists in offers list
        int itemOffer = this.offerMap.get(itemName) != null ? this.offerMap.get(itemName) : 0;

        // Create a new item
        Item item = new Item(itemName, itemPrice, itemOffer);

        // Add item to basket
        basket.addItem(item);
    }

    public double getSubTotal(Basket basket) {
        // Stream through all items in the basket
        return basket.getItemList().stream()
                // Extract the price of each item
                .map(Item::getPrice)
                // Sum all the prices together
                .reduce(Double::sum)
                // If no items are present, return 0.0 as the default
                .orElse(0.0);
    }


    public List<String> getOffers(Basket basket) {
        // Initialize a list to store offer descriptions
        List<String> offerList = new ArrayList<>();

        // Iterate over each item and its corresponding discount offer
        this.offerMap.forEach((name, offer) -> {
            // Calculate the discount in pennies for the given item
            double itemDiscount = this.discountService.getItemDiscountInPennies(basket.getItemList(), name);

            // If the discount is £1 or more, format it with pound symbol
            if (itemDiscount >= 100.0) {
                offerList.add(name + " " + offer + "% off:-£" + itemDiscount / 100);
            }
            // If there's a non-zero discount less than £1, format it in pence
            else if (itemDiscount != 0) {
                offerList.add(name + " " + offer + "% off:-" + (int) itemDiscount + "p");
            }
        });

        // If no discounts were applied, show a default message
        if (offerList.isEmpty()) {
            offerList.add("(No offers available)");
        }

        // Return the list of formatted offers
        return offerList;
    }


    public double getTotalPrice(Basket basket) {
        // Calculate the subtotal of all items in the basket (before discounts)
        double subtotal = this.getSubTotal(basket);

        // Calculate the total discount applied to the basket
        double totalDiscount = this.discountService.getTotalDiscount(basket.getItemList());

        // Subtract the discount from the subtotal to get the final total price
        return (subtotal - totalDiscount);
    }

}
