package shipping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShippingConfigTest {
    Order order = new Order("o1", "c1", "Medellín", 100, 4);

    @Test
    void checkoutDeProduccionUsaLegacy() {
        assertEquals(110.0, ShippingConfig.checkout().total(order));
    }
}
