package com.pricebasket;

import com.pricebasket.controllers.BasketController;
import com.pricebasket.controllers.IBasketController;
import com.pricebasket.models.Basket;
import com.pricebasket.services.BasketService;
import com.pricebasket.services.DiscountService;
import com.pricebasket.services.IBasketService;
import com.pricebasket.services.IDiscountService;

import static com.pricebasket.constants.DiscountConstants.OFFER_MAP;
import static com.pricebasket.constants.PriceConstants.PRICE_MAP;

public class PriceBasket {

    public static void main(String[] args) {

        /* Create services and controller instances */
        IDiscountService discountService = new DiscountService(PRICE_MAP, OFFER_MAP);

        IBasketService basketService = new BasketService(discountService);

        IBasketController basketController = new BasketController(basketService);

        // Validate items passed by user as inputs
        if (basketController.areValidItems(args)) {

            // Create new basket
            Basket basket = new Basket();

            // Add passed items in basket
            basketController.addItemsToBasket(args, basket);

            // Display Outputs
            basketController.displayBasketPrice(basket);
        }
        else {
            // Print a message if items are not existing products
            System.err.println("Invalid models.Basket, verify the goods.\nThe program exists!");
        }
    }

}