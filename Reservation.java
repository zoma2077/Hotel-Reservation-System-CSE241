import java.time.LocalDate;

public class Reservation {
    private Guest guest;
    private Room room;
    private int days;
    private LocalDate checkInDate;
    private exceptions.ReservationStatus status;

    public Reservation(Guest guest, Room room, int days) {
        this.guest = guest;
        this.room = room;
        this.days = days;
        this.checkInDate = LocalDate.now();
        this.status = exceptions.ReservationStatus.CONFIRMED;
    }

    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public int getDays() { return days; }
    public exceptions.ReservationStatus getStatus() { return status; }
    public void setStatus(exceptions.ReservationStatus status) { this.status = status; }
}
