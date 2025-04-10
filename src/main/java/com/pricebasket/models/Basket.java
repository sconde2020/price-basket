package com.pricebasket.models;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    private final List<Item> itemList;

    public Basket() {
        this.itemList = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.itemList.add(item);
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
