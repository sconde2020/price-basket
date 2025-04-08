package discount;

import java.util.Map;

public interface IDiscountBuilder {
    static IDiscount createDiscount(Map<String, Double> priceMap, Map<String, Integer> offerMap) {
        return new Discount(priceMap, offerMap);
    }
}
