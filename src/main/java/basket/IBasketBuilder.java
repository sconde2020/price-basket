package basket;

import discount.IDiscount;

import java.util.Map;

public interface IBasketBuilder {

    static IBasket createBasket(IDiscount discount, Map<String, Double> priceMap, Map<String, Integer> offerMap) {
        return new Basket(discount, priceMap, offerMap);
    }

}
