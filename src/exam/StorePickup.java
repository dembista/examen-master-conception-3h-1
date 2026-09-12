package exam;

public final class StorePickup implements ShippingStrategy {
    @Override
    public long feesCents(long subtotalCents, long totalWeightGrams) {
        return 0;
    }
}
