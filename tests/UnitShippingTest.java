package exam;

public final class UnitShippingTest {
    public static void main(String[] args) {
        standardJustBelowAndAtThreshold();
        expressAtOneKilogramAndJustAbove();
        System.out.println("UnitShippingTest: 2 scénarios réussis");
    }

    private static void standardJustBelowAndAtThreshold() {
        Configuration configuration = new TestConfiguration(10_000, 30_000);
        ShippingStrategy standard = new StandardShipping(configuration);
        TestSupport.assertEquals(500, standard.feesCents(9_999, 1), "frais sous le seuil");
        TestSupport.assertEquals(0, standard.feesCents(10_000, 1), "gratuité au seuil");
    }

    private static void expressAtOneKilogramAndJustAbove() {
        ShippingStrategy express = new ExpressShipping();
        TestSupport.assertEquals(1_200, express.feesCents(0, 1_000), "frais pour 1 000 g");
        TestSupport.assertEquals(1_400, express.feesCents(0, 1_001), "frais pour 1 001 g");
    }

    record TestConfiguration(long freeShippingThresholdCents, long maximumWeightGrams)
            implements Configuration {
    }
}
