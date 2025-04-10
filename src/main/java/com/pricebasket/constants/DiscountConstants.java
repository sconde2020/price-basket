package com.pricebasket.constants;

import java.util.Map;

public class DiscountConstants {
    public static final Map<String, Integer> OFFER_MAP = Map.of(
            "Apples", 10, // -10% this week
            "Bread", 50 // -50% with 2 tins of Soup
    );
}
