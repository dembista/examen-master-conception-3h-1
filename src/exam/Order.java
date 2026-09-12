package exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Order {
    private final String customerId;
    private final Configuration configuration;
    private final Notifier notifier;
    private final List<OrderLine> lines = new ArrayList<>();
    private ShippingStrategy shippingStrategy;
    private OrderState state = OrderState.BROUILLON;
    private Long validatedTotalCents;

    public Order(String customerId, ShippingStrategy shippingStrategy,
                 Configuration configuration, Notifier notifier) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("L'identifiant client est obligatoire");
        }
        if (shippingStrategy == null || configuration == null || notifier == null) {
            throw new IllegalArgumentException("Les dépendances de la commande sont obligatoires");
        }
        if (configuration.maximumWeightGrams() <= 0 || configuration.freeShippingThresholdCents() < 0) {
            throw new IllegalArgumentException("La configuration est invalide");
        }
        this.customerId = customerId;
        this.shippingStrategy = shippingStrategy;
        this.configuration = configuration;
        this.notifier = notifier;
    }

    public void addLine(Product product, int quantity) {
        ensureDraft();
        if (product == null) throw new IllegalArgumentException("Le produit est obligatoire");
        if (quantity <= 0) throw new IllegalArgumentException("La quantité doit être strictement positive");
        lines.add(new OrderLine(product, quantity));
    }

    public void changeShippingStrategy(ShippingStrategy shippingStrategy) {
        ensureDraft();
        if (shippingStrategy == null) throw new IllegalArgumentException("La stratégie est obligatoire");
        this.shippingStrategy = shippingStrategy;
    }

    public long subtotalCents() {
        long subtotal = 0;
        for (OrderLine line : lines) {
            subtotal = Math.addExact(subtotal, Math.multiplyExact(line.product().priceCents(), line.quantity()));
        }
        return subtotal;
    }

    public long totalWeightGrams() {
        long weight = 0;
        for (OrderLine line : lines) {
            weight = Math.addExact(weight, Math.multiplyExact(line.product().weightGrams(), line.quantity()));
        }
        return weight;
    }

    public long shippingFeesCents() {
        return shippingStrategy.feesCents(subtotalCents(), totalWeightGrams());
    }

    public long totalCents() {
        if (validatedTotalCents != null) return validatedTotalCents;
        return Math.addExact(subtotalCents(), shippingFeesCents());
    }

    public void validate() {
        ensureDraft();
        if (lines.isEmpty()) throw new BusinessRuleException("Une commande vide ne peut pas être validée");
        if (totalWeightGrams() > configuration.maximumWeightGrams()) {
            throw new BusinessRuleException("Le poids maximal de la commande est dépassé");
        }
        validatedTotalCents = totalCents();
        state = OrderState.VALIDEE;
        notifier.notify(customerId, validatedTotalCents);
    }

    public String customerId() {
        return customerId;
    }

    public OrderState state() {
        return state;
    }

    public List<OrderLine> lines() {
        return Collections.unmodifiableList(lines);
    }

    private void ensureDraft() {
        if (state != OrderState.BROUILLON) {
            throw new BusinessRuleException("La commande est déjà validée");
        }
    }

    public record OrderLine(Product product, int quantity) {
        public OrderLine {
            if (product == null || quantity <= 0) {
                throw new IllegalArgumentException("Ligne de commande invalide");
            }
        }
    }
}
