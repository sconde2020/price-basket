package com.pricebasket.controllers;


import com.pricebasket.models.Basket;
import com.pricebasket.services.IBasketService;

import java.util.Locale;

public class BasketController implements IBasketController {

    private final IBasketService basketService ;

    public BasketController(IBasketService basketService ) {
        this.basketService = basketService;
    }

    public boolean areValidItems(String[] itemNames) {
        return this.basketService.isValidBasket(itemNames);
    }

    public void addItemsToBasket(String[] itemNames, Basket basket) {
        for (String itemName : itemNames) {
            basketService.addItem(itemName, basket);
        }
    }

    public void displayBasketPrice(Basket basket) {
        double subTotal = basketService.getSubTotal(basket);

        double total = basketService.getTotalPrice(basket);

        // Display Subtotal in pounds or pennies
        displayAmountInPoundsOrPennies(subTotal, "Subtotal");

        // Display eligible offers
        basketService.getOffers(basket).forEach(System.out::println);

        // Display total price in pounds or pennies
        displayAmountInPoundsOrPennies(total, "Total price");
    }

    private void displayAmountInPoundsOrPennies(double amountInPounds, String name) {
        if (amountInPounds >= 1) {
            System.out.printf(Locale.US, "%s: £%.2f%n", name, amountInPounds);
        }
        else {
            System.out.printf("%s: %dp%n", name, (int)(amountInPounds * 100));
        }
    }
}
