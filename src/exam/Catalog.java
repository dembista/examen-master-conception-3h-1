package exam;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class Catalog {
    private final String name;
    private final Set<Product> products = new LinkedHashSet<>();

    public Catalog(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom du catalogue est obligatoire");
        }
        this.name = name;
    }

    public void add(Product product) {
        if (product == null) throw new IllegalArgumentException("Le produit est obligatoire");
        products.add(product);
    }

    public String name() {
        return name;
    }

    public Set<Product> products() {
        return Collections.unmodifiableSet(products);
    }
}
