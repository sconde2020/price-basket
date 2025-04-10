package com.pricebasket.services;

import com.pricebasket.models.Basket;
import com.pricebasket.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BasketServiceTest {

    private IDiscountService discountService;
    private BasketService basketService;
    private Basket basket;

    @BeforeEach
    void setUp() {
        discountService = mock(IDiscountService.class);

        when(discountService.priceMap()).thenReturn(Map.of(
                "Apple", 0.60,
                "Milk", 1.00
        ));

        when(discountService.offerMap()).thenReturn(Map.of(
                "Apple", 10
        ));

        basketService = new BasketService(discountService);
        basket = new Basket();
    }

    @Test
    void testIsValidBasket_withValidItems() {
        String[] items = {"Apple", "Milk"};
        assertTrue(basketService.isValidBasket(items));
    }

    @Test
    void testIsValidBasket_withInvalidItem() {
        String[] items = {"Apple", "Bread"};
        assertFalse(basketService.isValidBasket(items));
    }

    @Test
    void testAddItem_addsCorrectItemToBasket() {
        basketService.addItem("Apple", basket);
        List<Item> items = basket.getItemList();
        assertEquals(1, items.size());
        assertEquals("Apple", items.getFirst().name());
        assertEquals(0.60, items.getFirst().price());
        assertEquals(10, items.getFirst().offer());
    }

    @Test
    void testGetSubTotal_returnsCorrectSum() {
        basketService.addItem("Apple", basket);
        basketService.addItem("Milk", basket);
        assertEquals(1.60, basketService.getSubTotal(basket));
    }

    @Test
    void testGetOffers_returnsFormattedOffer() {
        Item item = new Item("Apple", 0.60, 10);
        basket.addItem(item);

        when(discountService.getItemDiscountInPennies(anyList(), eq("Apple"))).thenReturn(6.0);

        List<String> offers = basketService.getOffers(basket);
        assertEquals(1, offers.size());
        assertEquals("Apple 10% off:-6p", offers.getFirst());
    }

    @Test
    void testGetOffers_withAtLeastOnePound_returnsFormattedOffer() {
        Item item = new Item("Apple", 0.60, 10);
        basket.addItem(item);

        when(discountService.getItemDiscountInPennies(anyList(), eq("Apple"))).thenReturn(120.0);

        List<String> offers = basketService.getOffers(basket);
        assertEquals(1, offers.size());
        assertEquals("Apple 10% off:-£1.20", offers.getFirst());
    }

    @Test
    void testGetOffers_returnsNoOffers() {
        when(discountService.getItemDiscountInPennies(anyList(), anyString())).thenReturn(0.0);
        List<String> offers = basketService.getOffers(basket);
        assertEquals(1, offers.size());
        assertEquals("(No offers available)", offers.getFirst());
    }

    @Test
    void testGetTotalPrice_returnsSubtotalMinusDiscount() {
        basketService.addItem("Apple", basket);
        basketService.addItem("Milk", basket);

        when(discountService.getTotalDiscount(anyList())).thenReturn(0.60);

        assertEquals(1.00, basketService.getTotalPrice(basket));
    }
}
