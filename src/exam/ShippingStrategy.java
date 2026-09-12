package exam;

public interface ShippingStrategy {
    long feesCents(long subtotalCents, long totalWeightGrams);
}
