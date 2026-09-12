package exam;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Configuration configuration = DefaultConfiguration.instance();
        Product product = new Product("LIVRE-001", 4_500, 900);
        Order order = new Order("client-42", new StandardShipping(configuration), configuration,
                new ConsoleNotifier());
        order.addLine(product, 2);
        System.out.println("Sous-total initial : " + order.subtotalCents() + " centimes");
        order.changeShippingStrategy(new ExpressShipping());
        System.out.println("Total express : " + order.totalCents() + " centimes");
        order.validate();
        System.out.println("État final : " + order.state() + ", total figé : " + order.totalCents() + " centimes");
    }
}
