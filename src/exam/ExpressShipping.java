package exam;

public final class ExpressShipping implements ShippingStrategy {
    @Override
    public long feesCents(long subtotalCents, long totalWeightGrams) {
        long kilogramsStarted = (totalWeightGrams + 999) / 1000;
        return 1_000 + 200 * kilogramsStarted;
    }
}
