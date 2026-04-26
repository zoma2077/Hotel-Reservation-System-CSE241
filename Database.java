import java.time.LocalDate;
import java.util.ArrayList;

public class Database {

    private static java.util.ArrayList<Guest> guests = new java.util.ArrayList<>();
    private static java.util.ArrayList<Staff> staffMembers = new java.util.ArrayList<>();
    private static java.util.ArrayList<Room> rooms = new java.util.ArrayList<>();
    private static java.util.ArrayList<RoomType> roomTypes = new java.util.ArrayList<>();
    private static java.util.ArrayList<Amenity> amenities = new java.util.ArrayList<>();
    private static java.util.ArrayList<Reservation> reservations = new java.util.ArrayList<>();
    private static java.util.ArrayList<Invoice> invoices = new java.util.ArrayList<>();


    //  Getters
    public static ArrayList<Guest> getGuests() { return guests; }
    public static ArrayList<Staff> getStaffMembers() { return staffMembers; }
    public static ArrayList<RoomType> getRoomTypes() { return roomTypes; }
    public static ArrayList<Amenity> getAmenities() { return amenities; }
    public static ArrayList<Room> getRooms() { return rooms; }

    public static void initialize() {
        //  Initialize  Amenities
        Amenity wifi = new Amenity("High-Speed Wi-Fi");
        Amenity tv = new Amenity("4K Smart TV");
        Amenity miniBar = new Amenity("Mini Bar");
        Amenity ac = new Amenity("Air Conditioning");
        Amenity balcony = new Amenity("Private Balcony");
        Amenity jacuzzi = new Amenity("Jacuzzi Tub");

        amenities.add(wifi);
        amenities.add(tv);
        amenities.add(miniBar);
        amenities.add(ac);
        amenities.add(balcony);
        amenities.add(jacuzzi);

        //  Initialize Room Types
        RoomType single = new RoomType(1, "Single", 100.0);
        RoomType doubleRoom = new RoomType(2, "Double", 150.0);
        RoomType suite = new RoomType(3, "Suite", 300.0);

        roomTypes.add(single);
        roomTypes.add(doubleRoom);
        roomTypes.add(suite);

        //  Initialize Rooms and Assign Specific Amenities
        Room r101 = new Room(101, single);
        r101.addAmenity(wifi);
        r101.addAmenity(ac);

        Room r102 = new Room(102, doubleRoom);
        r102.addAmenity(wifi);
        r102.addAmenity(tv);
        r102.addAmenity(ac);

        Room r201 = new Room(201, suite);
        r201.addAmenity(wifi);
        r201.addAmenity(tv);
        r201.addAmenity(miniBar);
        r201.addAmenity(jacuzzi);
        r201.addAmenity(balcony);

        rooms.add(r101);
        rooms.add(r102);
        rooms.add(r201);

        //  Initialize Staff
        staffMembers.add(new Admin("Saif", "Admin@2026", LocalDate.of(1985, 1, 1), 40));
        staffMembers.add(new Reception("Ellie", "Staff@2026", LocalDate.of(1995, 6, 15), 45));

        //  Existing Room/Guest
        guests.add(new Guest("Ahmed", "Ahmed@2026", LocalDate.of(1990, 5, 15), 1500.0, "USA-NY", exceptions.Gender.MALE, "High floor"));
        guests.add(new Guest("Sara", "Sara@2026", LocalDate.of(1985, 10, 20), 2000.0, "Canada-ON-Toronto-Main St", exceptions.Gender.FEMALE, "Suite, non-smoking"));
    }

    public static Staff staffLogin(String username, String password) {
        for (Staff s : staffMembers) {
            if (s.getUsername().equals(username) && s.getPassword().equals(password)) {
                return s;
            }
        }
        return null;
    }

    public static Room getRoomByNumber(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) return r;
        }
        return null;
    }
}







