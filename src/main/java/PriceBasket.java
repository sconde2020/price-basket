import basket.BasketHelper;
import basket.IBasket;

import static constants.DiscountConstants.OFFER_MAP;
import static constants.PriceConstants.PRICE_MAP;

public class PriceBasket {

    public static void main(String[] args) {

        BasketHelper basketHelper = new BasketHelper(PRICE_MAP, OFFER_MAP);

        if (basketHelper.areValidItems(args)) {

            IBasket basket = basketHelper.createBasket();

            basketHelper.addItemsToBasket(args, basket);

            basketHelper.displayBasketPrice(basket);
        }
        else {
            System.err.println("Invalid basket.Basket, verify the goods." +
                    "\nThe program exists!");
        }
    }

}