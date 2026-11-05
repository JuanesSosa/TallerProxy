package shipping;

import java.util.function.Consumer;

public class ShippingCalculatorProxy implements ShippingCalculator {
    private final ShippingCalculator legacy;
    private final ShippingCalculator smart;
    private final FeatureFlags flags;
    private final Consumer<String> log;

    /** Sin registro: conserva la firma original del Proxy. */
    public ShippingCalculatorProxy(ShippingCalculator legacy,
                                   ShippingCalculator smart,
                                   FeatureFlags flags) {
        this(legacy, smart, flags, message -> { });
    }

    public ShippingCalculatorProxy(ShippingCalculator legacy,
                                   ShippingCalculator smart,
                                   FeatureFlags flags,
                                   Consumer<String> log) {
        this.legacy = legacy;
        this.smart = smart;
        this.flags = flags;
        this.log = log;
    }

    @Override
    public double calculate(Order order) {
        ShippingCalculator selected = flags.isEnabled("smart-shipping") ? smart : legacy;
        long start = System.nanoTime();
        double shipping = selected.calculate(order);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        log.accept("shipping order=" + order.id()
                + " impl=" + selected.getClass().getSimpleName()
                + " time=" + elapsedMs + "ms");
        return shipping;
    }
}
