package controllers;

import models.Basket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.IBasketService;

import java.util.List;

import static org.mockito.Mockito.*;

class BasketControllerTest {

    private IBasketService basketService;
    private BasketController basketController;

    @BeforeEach
    void setUp() {
        basketService = mock(IBasketService.class);
        basketController = new BasketController(basketService);
    }

    @Test
    void testAreValidItems_returnsTrueWhenValid() {
        String[] items = {"Apples", "Bread"};
        when(basketService.isValidBasket(items)).thenReturn(true);

        assert basketController.areValidItems(items);
        verify(basketService).isValidBasket(items);
    }

    @Test
    void testAddItemsToBasket_callsServiceForEachItem() {
        String[] items = {"Apples", "Bread"};
        Basket basket = new Basket();

        basketController.addItemsToBasket(items, basket);

        verify(basketService, times(1)).addItem("Apples", basket);
        verify(basketService, times(1)).addItem("Bread", basket);
    }

    @Test
    void testDisplayBasketPrice_printsAllOutputs() {
        Basket basket = new Basket();

        when(basketService.getSubTotal(basket)).thenReturn(1.50);
        when(basketService.getTotalPrice(basket)).thenReturn(1.10);
        when(basketService.getOffers(basket)).thenReturn(List.of("Apples 10% off:-10p"));

        basketController.displayBasketPrice(basket);

        verify(basketService).getSubTotal(basket);
        verify(basketService).getTotalPrice(basket);
        verify(basketService).getOffers(basket);
    }

    @Test
    void testDisplayBasketPrice_printsInPennies() {
        Basket basket = new Basket();

        when(basketService.getSubTotal(basket)).thenReturn(0.80);
        when(basketService.getTotalPrice(basket)).thenReturn(0.65);
        when(basketService.getOffers(basket)).thenReturn(List.of("Bread 50% off:-40p"));

        basketController.displayBasketPrice(basket);
    }
}

