package basket;

import java.util.List;

public interface IBasket {
    void addItem(String itemName);

    double getSubTotal();

    List<String> getOffers();

    double getTotalPrice();

}
