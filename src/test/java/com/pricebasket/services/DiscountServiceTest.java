package com.pricebasket.services;

import com.pricebasket.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DiscountServiceTest {

    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountService(
                Map.of(
                        "Apples", 1.00,
                        "Soup", 0.65,
                        "Bread", 0.80
                ),
                Map.of(
                        "Apples", 10,
                        "Bread", 50
                )
        );
    }

    @Test
    void testGetItemDiscountInPennies_forApples() {
        List<Item> items = List.of(
                new Item("Apples", 1.00, 10),
                new Item("Apples", 1.00, 10)
        );

        double discount = discountService.getItemDiscountInPennies(items, "Apples");
        assertEquals(20.0, discount); // 2 items * 1.00 * 10 = 20.0
    }

    @Test
    void testGetItemDiscountInPennies_forBread_withSoup() {
        List<Item> items = List.of(
                new Item("Soup", 0.65, 0),
                new Item("Soup", 0.65, 0),
                new Item("Bread", 0.80, 50)
        );

        double discount = discountService.getItemDiscountInPennies(items, "Bread");
        assertEquals(40.0, discount); // 1 bread gets 50% off 0.80 = 40.0 pennies
    }

    @Test
    void testGetBreadDiscountInPennies_withMultipleBreadsAndSoups() {
        List<Item> items = List.of(
                new Item("Soup", 0.65, 0),
                new Item("Soup", 0.65, 0),
                new Item("Soup", 0.65, 0),
                new Item("Soup", 0.65, 0),
                new Item("Bread", 0.80, 50),
                new Item("Bread", 0.80, 50)
        );

        double discount = discountService.getBreadDiscountInPennies(items);
        assertEquals(80.0, discount); // 2 discounted breads (2 x 50% off 0.80)
    }

    @Test
    void testGetTotalDiscount_combinesMultipleDiscounts() {
        List<Item> items = List.of(
                new Item("Apples", 1.00, 10),
                new Item("Soup", 0.65, 0),
                new Item("Soup", 0.65, 0),
                new Item("Bread", 0.80, 50)
        );

        double discount = discountService.getTotalDiscount(items);
        assertEquals((10.0 + 40.0) / 100.0, discount); // 1 Apple 10% off = 10, 1 Bread 50% off = 40 pennies
    }

    @Test
    void testGetItemDiscountInPennies_forUnknownItem() {
        List<Item> items = List.of(new Item("Milk", 1.00, 0));
        double discount = discountService.getItemDiscountInPennies(items, "Milk");
        assertEquals(0.0, discount);
    }
}
