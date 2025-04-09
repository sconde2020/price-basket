package services;

import models.Item;

import java.util.List;
import java.util.Map;

public interface IDiscountService {
    Map<String, Double> priceMap();

    Map<String, Integer> offerMap();

    double getItemDiscountInPennies(List<Item> itemList, String itemName);

    double getBreadDiscountInPennies(List<Item> itemList);

    double getTotalDiscount(List<Item> itemList);
}
