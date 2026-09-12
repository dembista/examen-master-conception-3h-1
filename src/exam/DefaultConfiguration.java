package exam;

public final class DefaultConfiguration implements Configuration {
    private static final class Holder {
        private static final DefaultConfiguration INSTANCE = new DefaultConfiguration();
    }

    private DefaultConfiguration() {
    }

    public static DefaultConfiguration instance() {
        return Holder.INSTANCE;
    }

    @Override
    public long freeShippingThresholdCents() {
        return 10_000;
    }

    @Override
    public long maximumWeightGrams() {
        return 30_000;
    }
}
