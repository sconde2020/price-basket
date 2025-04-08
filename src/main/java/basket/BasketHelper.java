package basket;

import discount.IDiscount;
import discount.IDiscountBuilder;

import java.util.Arrays;
import java.util.Map;

public class BasketHelper {
    private final Map<String, Double> priceMap;
    private final Map<String, Integer> offerMap;

    public BasketHelper(Map<String, Double> priceMap, Map<String, Integer> offerMap) {
        this.priceMap = priceMap;
        this.offerMap = offerMap;
    }

    public boolean areValidItems(String[] itemNames) {
        return Arrays.stream(itemNames).allMatch(this.priceMap::containsKey);
    }

    public IBasket createBasket() {
        IDiscount discount = IDiscountBuilder.createDiscount(this.priceMap, this.offerMap);
        return IBasketBuilder.createBasket(discount, priceMap, offerMap);
    }

    public void addItemsToBasket(String[] itemNames, IBasket basket) {
        for (String itemName : itemNames) {
            basket.addItem(itemName);
        }
    }

    public void displayBasketPrice(IBasket basket) {
        double subTotal = basket.getSubTotal();

        double total = basket.getTotalPrice();

        displayAmountInPoundsOrPennies(subTotal, "Subtotal");

        basket.getOffers().forEach(System.out::println);

        displayAmountInPoundsOrPennies(total, "Total");
    }

    public void displayAmountInPoundsOrPennies(double amountInPounds, String name) {
        if (amountInPounds >= 1) {
            System.out.printf("%s: £%.2f%n", name, amountInPounds);
        }
        else {
            System.out.printf("%s: %dp%n", name, (int)(amountInPounds * 100));
        }
    }
}
