package shipping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckoutTest {
    Order order = new Order("o1", "c1", "Medellín", 100, 4);

    @Test
    void totalSumaSubtotalYEnvioLegacy() {
        assertEquals(110.0, new Checkout().total(order));
    }
}
