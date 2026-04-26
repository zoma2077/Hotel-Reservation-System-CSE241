import java.util.ArrayList;

public class Room {
    private int roomNumber;
    private RoomType roomType;
    private ArrayList<Amenity> amenities;
    private boolean isAvailable;

    public Room(int roomNumber, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.amenities = new ArrayList<>();
        this.isAvailable = true; // Default to true when created
    }

    public void addAmenity(Amenity amenity) {
        amenities.add(amenity);
    }

    public int getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public ArrayList<Amenity> getAmenities() { return amenities; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}