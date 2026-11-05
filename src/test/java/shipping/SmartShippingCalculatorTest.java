package shipping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmartShippingCalculatorTest {
    SmartShippingCalculator smart = new SmartShippingCalculator();

    @Test void cobra2punto5PorKilo() {
        Order order = new Order("o1", "c1", "Bogotá", 100, 4);
        assertEquals(10.0, smart.calculate(order));
    }

    @Test void elCostoCreceConElPeso() {
        Order order = new Order("o2", "c1", "Bogotá", 100, 10);
        assertEquals(25.0, smart.calculate(order));
    }
}
