package shipping;

/** Punto de ensamblaje: aquí se decide con qué calculador se construye el Checkout. */
public final class ShippingConfig {

    private ShippingConfig() {
    }

    public static Checkout checkout() {
        return new Checkout(new LegacyShippingCalculator());
    }
}
