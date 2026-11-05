package shipping;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShippingCalculatorProxyLoggingTest {
    Order order = new Order("o1", "c1", "Medellín", 100, 4);
    FeatureFlags flags = new FeatureFlags();
    List<String> log = new ArrayList<>();
    ShippingCalculatorProxy proxy = new ShippingCalculatorProxy(
            new LegacyShippingCalculator(), new SmartShippingCalculator(), flags, log::add);

    @Test void registraLegacyYLosMilisegundosConFlagApagado() {
        proxy.calculate(order);

        assertEquals(1, log.size());
        assertTrue(log.get(0).contains("order=o1"));
        assertTrue(log.get(0).contains("impl=LegacyShippingCalculator"));
        assertTrue(log.get(0).matches(".*time=\\d+ms"));
    }

    @Test void registraSmartConFlagEncendido() {
        flags.enable("smart-shipping");

        proxy.calculate(order);

        assertEquals(1, log.size());
        assertTrue(log.get(0).contains("impl=SmartShippingCalculator"));
        assertTrue(log.get(0).matches(".*time=\\d+ms"));
    }

    @Test void registrarNoCambiaElResultado() {
        assertEquals(10.0, proxy.calculate(order));

        flags.enable("smart-shipping");
        assertEquals(15.0, proxy.calculate(order));
        assertEquals(2, log.size());
    }
}
