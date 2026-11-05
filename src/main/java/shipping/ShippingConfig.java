package shipping;

/** Punto de ensamblaje: aquí se decide con qué calculador se construye el Checkout. */
public final class ShippingConfig {

    private ShippingConfig() {
    }

    /** Flags de producción. smart-shipping encendido: el envío se calcula con Smart. */
    public static FeatureFlags productionFlags() {
        FeatureFlags flags = new FeatureFlags();
        flags.enable("smart-shipping");
        return flags;
    }

    public static Checkout checkout() {
        return checkout(productionFlags());
    }

    public static Checkout checkout(FeatureFlags flags) {
        return new Checkout(new ShippingCalculatorProxy(
                new LegacyShippingCalculator(), new SmartShippingCalculator(), flags));
    }
}
