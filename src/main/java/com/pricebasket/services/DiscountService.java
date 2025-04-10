package com.pricebasket.services;

import com.pricebasket.models.Item;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public record DiscountService(Map<String, Double> priceMap, Map<String, Integer> offerMap) implements IDiscountService {

    public double getItemDiscountInPennies(List<Item> itemList, String itemName) {
        double discount = 0.0;

        // General discount (discount = quantity * offer * price)
        if (itemName.equals("Apples")) {
            long quantity = itemList.stream().filter(item -> item.name().equals(itemName)).count();
            return (this.priceMap.get(itemName) * quantity * this.offerMap.get(itemName));
        }

        // Special Case for bread with a specific rule
        if (itemName.equals("Bread")) {
            return this.getBreadDiscountInPennies(itemList);
        }

        return discount;
    }

    public double getBreadDiscountInPennies(List<Item> itemList) {
        // Count the number of "Soup" items in the basket
        long soupQuantity = itemList.stream().filter(item -> item.name().equals("Soup")).count();

        // Count the number of "Bread" items in the basket
        long breadQuantity = itemList.stream().filter(item -> item.name().equals("Bread")).count();

        // For every 2 soups, one bread gets a discount
        long discountedBreads = Math.min(soupQuantity / 2, breadQuantity);

        // Calculate the total discount for bread based on the eligible discounted breads
        return (discountedBreads * this.priceMap.get("Bread") * this.offerMap.get("Bread"));
    }

    public double getTotalDiscount(List<Item> itemList) {
        // Create total discount variable as atomic  in order to use Lambda expression
        AtomicReference<Double> totalDiscount = new AtomicReference<>(0.0);

        // Add each eligible discount of the current basket to total discount
        this.offerMap.forEach((name, _) -> {
                    totalDiscount.updateAndGet(value -> value + getItemDiscountInPennies(itemList, name));
        });

        // Convert computed amount in pounds
        return totalDiscount.get() / 100.0;
    }

}
