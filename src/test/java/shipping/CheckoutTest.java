package shipping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckoutTest {
    Order order = new Order("o1", "c1", "Medellín", 100, 4);

    @Test
    void totalSumaSubtotalYEnvioLegacy() {
        Checkout checkout = new Checkout(new LegacyShippingCalculator());
        assertEquals(110.0, checkout.total(order));
    }

    @Test
    void checkoutFuncionaConCualquierCalculador() {
        Checkout checkout = new Checkout(o -> 7.0);
        assertEquals(107.0, checkout.total(order));
    }
}
