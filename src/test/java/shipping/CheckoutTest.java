package shipping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckoutTest {
    Order order = new Order("o1", "c1", "Medellín", 100, 4);

    @Test
    void totalSumaSubtotalYEnvioSmart() {
        Checkout checkout = new Checkout(new SmartShippingCalculator());
        assertEquals(115.0, checkout.total(order)); // 100 + (4 kg × 2,5 + 5)
    }

    @Test
    void checkoutFuncionaConCualquierCalculador() {
        Checkout checkout = new Checkout(o -> 7.0);
        assertEquals(107.0, checkout.total(order));
    }
}
