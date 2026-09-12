package exam;

import java.util.Objects;

public class Product {
    private final String reference;
    private final long priceCents;
    private final long weightGrams;

    public Product(String reference, long priceCents, long weightGrams) {
        if (reference == null || reference.isBlank()) {
            throw new IllegalArgumentException("La référence est obligatoire");
        }
        if (priceCents < 0) {
            throw new IllegalArgumentException("Le prix ne peut pas être négatif");
        }
        if (weightGrams <= 0) {
            throw new IllegalArgumentException("Le poids doit être strictement positif");
        }
        this.reference = reference;
        this.priceCents = priceCents;
        this.weightGrams = weightGrams;
    }

    public String reference() {
        return reference;
    }

    public long priceCents() {
        return priceCents;
    }

    public long weightGrams() {
        return weightGrams;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Product product)) return false;
        return reference.equals(product.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }
}
