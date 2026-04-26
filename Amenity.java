public class Amenity {
    private String name;

    public Amenity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    @Override
    public String toString() {
        return "Amenity: " + name;
    }
}