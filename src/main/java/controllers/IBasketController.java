package controllers;

import models.Basket;

public interface IBasketController {

    boolean areValidItems(String[] itemNames);

    void addItemsToBasket(String[] itemNames, Basket basket);

    void displayBasketPrice(Basket basket);
}
