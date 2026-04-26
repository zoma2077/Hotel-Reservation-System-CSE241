interface Payable {
    void processPayment(double amount, exceptions.PaymentMethod method);
}

interface Manageable {
    void performRoleDuty();
}