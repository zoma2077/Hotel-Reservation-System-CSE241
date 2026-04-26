import java.time.LocalDate;

public class Invoice {
    private double totalAmount;
    private exceptions.PaymentMethod paymentMethod;
    private LocalDate paymentDate;

    public Invoice(double amount, exceptions.PaymentMethod method) {
        this.totalAmount = amount;
        this.paymentMethod = method;
        this.paymentDate = LocalDate.now();
    }
}