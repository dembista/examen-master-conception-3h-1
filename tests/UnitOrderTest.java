package exam;

public final class UnitOrderTest {
    public static void main(String[] args) {
        totalChangesWithTwoFixedStrategies();
        modificationsAreRejectedAfterValidation();
        System.out.println("UnitOrderTest: 2 scénarios réussis");
    }

    private static void totalChangesWithTwoFixedStrategies() {
        Configuration configuration = new UnitConfiguration(10_000, 30_000);
        Product product = new Product("P-1", 1_000, 100);
        Order order = new Order("client", new FixedShipping(100), configuration, (id, total) -> { });
        order.addLine(product, 2);
        TestSupport.assertEquals(2_100, order.totalCents(), "total avec première stratégie");
        order.changeShippingStrategy(new FixedShipping(700));
        TestSupport.assertEquals(2_700, order.totalCents(), "total avec deuxième stratégie");
    }

    private static void modificationsAreRejectedAfterValidation() {
        Configuration configuration = new UnitConfiguration(10_000, 30_000);
        Order order = new Order("client", new FixedShipping(0), configuration, (id, total) -> { });
        order.addLine(new Product("P-2", 1_000, 100), 1);
        order.validate();
        TestSupport.assertThrows(BusinessRuleException.class,
                () -> order.addLine(new Product("P-3", 100, 100), 1), "ajout après validation");
        TestSupport.assertThrows(BusinessRuleException.class,
                () -> order.changeShippingStrategy(new FixedShipping(10)), "changement après validation");
        TestSupport.assertThrows(BusinessRuleException.class, order::validate, "seconde validation");
    }

    record UnitConfiguration(long freeShippingThresholdCents, long maximumWeightGrams)
            implements Configuration {
    }

    record FixedShipping(long amountCents) implements ShippingStrategy {
        @Override
        public long feesCents(long subtotalCents, long totalWeightGrams) {
            return amountCents;
        }
    }
}
