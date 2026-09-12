package exam;

public final class AllTests {
    private AllTests() {
    }

    public static void main(String[] args) {
        UnitShippingTest.main(args);
        UnitOrderTest.main(args);
        IntegrationOrderTest.main(args);
        System.out.println("Tous les scénarios automatisés sont réussis.");
    }
}
