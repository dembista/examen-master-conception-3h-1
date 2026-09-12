package exam;

public final class ConsoleNotifier implements Notifier {
    @Override
    public void notify(String customerId, long validatedTotalCents) {
        System.out.println("Commande validée pour " + customerId + " : " + validatedTotalCents + " centimes");
    }
}
