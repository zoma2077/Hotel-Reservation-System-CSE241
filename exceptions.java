public class exceptions{


    public enum Gender {MALE, FEMALE;}

    public enum Job {ADMIN, RECEPTIONIST}

    public enum ReservationStatus {PENDING, CONFIRMED, CANCELLED, COMPLETED}

    public enum PaymentMethod {CASH, CREDIT_CARD, ONLINE}

    //  CUSTOM EXCEPTIONS
    public class RoomNotAvailableException extends Exception {
        public RoomNotAvailableException(String message) {
            super(message);
        }
    }

    public class InvalidPaymentException extends Exception {
        public InvalidPaymentException(String message) {
            super(message);
        }
    }
    static class InvalidDateException extends RuntimeException {
        public InvalidDateException(String message) {
            super(message);
        }
    }
}