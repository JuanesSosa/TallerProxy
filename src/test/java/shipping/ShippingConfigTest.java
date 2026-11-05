package shipping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShippingConfigTest {
    Order order = new Order("o1", "c1", "Medellín", 100, 4);

    @Test
    void checkoutDeProduccionUsaSmartConElFlagEncendido() {
        assertEquals(115.0, ShippingConfig.checkout().total(order)); // 100 + (4 kg × 2,5 + 5)
    }

    @Test
    void conElFlagApagadoSeguiriaUsandoLegacy() {
        assertEquals(110.0, ShippingConfig.checkout(new FeatureFlags()).total(order)); // 100 + 10
    }
}
