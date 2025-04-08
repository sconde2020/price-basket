package discount;

import models.Item;

import java.util.List;

public interface IDiscount {
    double getItemDiscountInPennies(List<Item> itemList, String itemName);

    double getBreadDiscountInPennies(List<Item> itemList);

    double getTotalDiscount(List<Item> itemList);
}
