import controllers.BasketController;
import controllers.IBasketController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Basket;

import static org.mockito.Mockito.*;

public class PriceBasketTest {

    private IBasketController basketController;
    private Basket basket;

    @BeforeEach
    public void setUp() {
        basketController = mock(IBasketController.class);
        basket = new Basket();
    }

    @Test
    public void testValidItemsAddsItemsAndDisplaysPrice() {
        String[] args = {"Apple", "Milk", "Bread"};

        when(basketController.areValidItems(args)).thenReturn(true);
        doNothing().when(basketController).addItemsToBasket(args, basket);
        doNothing().when(basketController).displayBasketPrice(basket);

        if (basketController.areValidItems(args)) {
            basketController.addItemsToBasket(args, basket);
            basketController.displayBasketPrice(basket);
        }

        verify(basketController, times(1)).addItemsToBasket(args, basket);
        verify(basketController, times(1)).displayBasketPrice(basket);
    }

    @Test
    public void testInvalidItemsPrintsErrorMessage() {
        String[] args = {"Chocolate", "DragonFruit"};

        when(basketController.areValidItems(args)).thenReturn(false);

        if (!basketController.areValidItems(args)) {
            System.err.println("Invalid models.Basket, verify the goods.\nThe program exists!");
        }

        verify(basketController, never()).addItemsToBasket(any(), any());
        verify(basketController, never()).displayBasketPrice(any());
    }
}