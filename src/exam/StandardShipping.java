package exam;

public final class StandardShipping implements ShippingStrategy {
    private final Configuration configuration;

    public StandardShipping(Configuration configuration) {
        this.configuration = requireConfiguration(configuration);
    }

    @Override
    public long feesCents(long subtotalCents, long totalWeightGrams) {
        return subtotalCents >= configuration.freeShippingThresholdCents() ? 0 : 500;
    }

    private static Configuration requireConfiguration(Configuration configuration) {
        if (configuration == null) throw new IllegalArgumentException("La configuration est obligatoire");
        return configuration;
    }
}
