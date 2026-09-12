package exam;

import java.util.ArrayList;
import java.util.List;

public final class IntegrationOrderTest {
    public static void main(String[] args) {
        validatesAndNotifiesExactlyOnce();
        rejectsOverweightWithoutNotification();
        System.out.println("IntegrationOrderTest: 2 scénarios réussis");
    }

    private static void validatesAndNotifiesExactlyOnce() {
        Configuration configuration = DefaultConfiguration.instance();
        RecordingNotifier notifier = new RecordingNotifier();
        Order order = new Order("client-integration", new ExpressShipping(), configuration, notifier);
        order.addLine(new Product("P-INT", 2_000, 1_001), 1);
        order.validate();
        TestSupport.assertEquals(OrderState.VALIDEE, order.state(), "état après validation");
        TestSupport.assertEquals(3_400, order.totalCents(), "total validé");
        TestSupport.assertEquals(1, notifier.messages.size(), "nombre de notifications");
        TestSupport.assertEquals("client-integration:3400", notifier.messages.get(0), "contenu notification");
    }

    private static void rejectsOverweightWithoutNotification() {
        Configuration configuration = new LimitedConfiguration(10_000, 1_000);
        RecordingNotifier notifier = new RecordingNotifier();
        Order order = new Order("client-overweight", new StorePickup(), configuration, notifier);
        order.addLine(new Product("P-HEAVY", 500, 1_001), 1);
        TestSupport.assertThrows(BusinessRuleException.class, order::validate, "refus du poids maximal");
        TestSupport.assertEquals(OrderState.BROUILLON, order.state(), "état après refus");
        TestSupport.assertEquals(0, notifier.messages.size(), "aucune notification après refus");
    }

    static final class RecordingNotifier implements Notifier {
        private final List<String> messages = new ArrayList<>();

        @Override
        public void notify(String customerId, long validatedTotalCents) {
            messages.add(customerId + ":" + validatedTotalCents);
        }
    }

    record LimitedConfiguration(long freeShippingThresholdCents, long maximumWeightGrams)
            implements Configuration {
    }
}
