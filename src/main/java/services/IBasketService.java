package services;

import models.Basket;

import java.util.List;

public interface IBasketService {
    boolean isValidBasket(String[] itemNames);

    void addItem(String itemName, Basket basket);

    double getSubTotal(Basket basket);

    List<String> getOffers(Basket basket);

    double getTotalPrice(Basket basket);
}
