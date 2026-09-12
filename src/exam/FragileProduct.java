package exam;

public final class FragileProduct extends Product {
    private final String packagingInstruction;

    public FragileProduct(String reference, long priceCents, long weightGrams, String packagingInstruction) {
        super(reference, priceCents, weightGrams);
        if (packagingInstruction == null || packagingInstruction.isBlank()) {
            throw new IllegalArgumentException("La consigne d'emballage est obligatoire");
        }
        this.packagingInstruction = packagingInstruction;
    }

    public String packagingInstruction() {
        return packagingInstruction;
    }
}
