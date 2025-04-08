package discount;

import models.Item;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class Discount implements IDiscount {

    private final Map<String, Double> priceMap;
    private final Map<String, Integer> offerMap;

    public Discount(Map<String, Double> priceMap, Map<String, Integer> offerMap) {
        this.priceMap = priceMap;
        this.offerMap = offerMap;
    }

    public double getItemDiscountInPennies(List<Item> itemList, String itemName) {
        double discount = 0.0;

        // General discount (quantity * offer * price)
        if (itemName.equals("Apples")) {
            long quantity = itemList.stream().filter(item -> item.getName().equals(itemName)).count();
            return (this.priceMap.get(itemName) * quantity * this.offerMap.get(itemName));
        }
        // Special Case for bread discount
        if (itemName.equals("Bread")) {
           return this.getBreadDiscountInPennies(itemList);
        }
        return discount;
    }

    public double getBreadDiscountInPennies(List<Item> itemList) {
        long soupQuantity = itemList.stream().filter(item -> item.getName().equals("Soup")).count();
        long breadQuantity = itemList.stream().filter(item -> item.getName().equals("Bread")).count();
        long discountedBreads = Math.min(soupQuantity/2, breadQuantity);
        return (discountedBreads * this.priceMap.get("Bread") * this.offerMap.get("Bread"));
    }

    public double getTotalDiscount(List<Item> itemList) {
        AtomicReference<Double> totalDiscount = new AtomicReference<>(0.0);
        this.offerMap.forEach(
                (name, _) -> {
                    totalDiscount.updateAndGet(value -> value + getItemDiscountInPennies(itemList, name));
                }
        );
        return totalDiscount.get() / 100.0;
    }

}
