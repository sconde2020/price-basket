import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceBasketIntegrationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void testValidInput_withOffers() {
        String[] args = {"Apples", "Milk", "Bread"};

        PriceBasket.main(args);

        String output = outContent.toString();
        assertTrue(output.contains("Subtotal: £3.10"));
        assertTrue(output.contains("Apples 10% off:-10p"));
        assertTrue(output.contains("Total price: £3.00"));
    }

    @Test
    void testValidInput_withNoOffers() {
        String[] args = {"Milk"};

        PriceBasket.main(args);

        String output = outContent.toString();
        assertTrue(output.contains("Subtotal: £1.30"));
        assertTrue(output.contains("(No offers available)"));
        assertTrue(output.contains("Total price: £1.30"));
    }

    @Test
    void testInvalidInput_printsErrorMessage() {
        String[] args = {"Banana", "Pizza"};

        PriceBasket.main(args);

        String error = errContent.toString();
        assertTrue(error.contains("Invalid models.Basket, verify the goods"));
    }
}
