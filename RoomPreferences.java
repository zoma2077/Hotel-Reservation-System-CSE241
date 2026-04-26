public class RoomPreferences {
    private String roomType;
    private int floor;

    public RoomPreferences(String roomType, int floor) {
        this.roomType = roomType;
        this.floor = floor;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getFloor() {
        return floor;
    }
}