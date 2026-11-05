package shipping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShippingCalculatorProxyTest {
    Order order = new Order("o1", "c1", "Medellín", 100, 4);
    FeatureFlags flags = new FeatureFlags();
    ShippingCalculator smartStub = o -> 0.0; // temporal: Smart aún no existe
    ShippingCalculatorProxy proxy = new ShippingCalculatorProxy(
            new LegacyShippingCalculator(), smartStub, flags);

    @Test void usaLegacyConFlagApagado() {
        assertEquals(10.0, proxy.calculate(order));
    }

    @Test void conFlagEncendidoDelegaEnElCalculadorNuevo() {
        flags.enable("smart-shipping");
        assertEquals(0.0, proxy.calculate(order));
    }

    @Test void checkoutFuncionaConCualquierCalculador() {
        Checkout checkout = new Checkout(o -> 7.0);
        assertEquals(107.0, checkout.total(order));
    }
}
