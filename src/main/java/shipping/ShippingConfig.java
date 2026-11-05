package shipping;

/** Punto de ensamblaje: aquí se decide con qué calculador se construye el Checkout. */
public final class ShippingConfig {

    private ShippingConfig() {
    }

    /** Flags de producción. Todo apagado: el sistema se comporta igual que antes. */
    public static FeatureFlags productionFlags() {
        return new FeatureFlags();
    }

    public static Checkout checkout() {
        return checkout(productionFlags());
    }

    public static Checkout checkout(FeatureFlags flags) {
        ShippingCalculator smartStub = order -> 0.0; // temporal: Smart aún no existe
        return new Checkout(new ShippingCalculatorProxy(new LegacyShippingCalculator(), smartStub, flags));
    }
}
