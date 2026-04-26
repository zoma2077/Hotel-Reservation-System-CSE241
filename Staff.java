import java.time.LocalDate;
import java.util.Scanner;

public abstract class Staff {
    protected String username;
    protected String password;
    protected LocalDate dateOfBirth;
    protected exceptions.Job job;
    protected int workingHours;

    private static final Scanner input = new Scanner(System.in);

    public Staff(String username, String password, LocalDate dateOfBirth, exceptions.Job job, int workingHours) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.job = job;
        this.workingHours = workingHours;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public exceptions.Job getJob() { return job; }

    //  STAFF REGISTER  METHOD
    public static Staff register() {
        System.out.println("\n--- Staff Registration ---");
        String username = fillUsername();
        String password = fillPassword();
        LocalDate dob = fillDateOfBirth();
        int hours = fillWorkingHours();

        int roleChoice = fillJobChoice();

        Staff newStaff;
        if (roleChoice == 1) {
            newStaff = new Admin(username, password, dob, hours);
            System.out.println("Role assigned: ADMIN");
        } else {
            newStaff = new Reception(username, password, dob, hours);
            System.out.println("Role assigned: RECEPTIONIST");
        }

        Database.getStaffMembers().add(newStaff);
        return newStaff;
    }

    private static int fillJobChoice() {
        while (true) {
            System.out.println("Select Role: 1. Admin  2. Receptionist");
            System.out.print("Choice: ");
            try {
                int choice = Integer.parseInt(input.nextLine().trim());
                if (choice == 1 || choice == 2) return choice;
                System.out.println("Please enter 1 or 2.");
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }


    private static String fillUsername() {
        String username;
        while (true) {
            System.out.print("Enter staff username: ");
            username = input.nextLine().trim();


            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
            } else if (username.length() < 3) {
                System.out.println("Username must be at least 3 characters.");
            } else if (!username.matches("[a-zA-Z0-9_]+")) {
                System.out.println("Username can only contain letters, numbers, and underscores.");
            } else {
                //  Uniqueness Validation
                boolean exists = false;
                for (Staff s : Database.getStaffMembers()) {
                    if (s.getUsername().equalsIgnoreCase(username)) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    System.out.println("Username already exists. Please choose a different one.");
                } else {
                    break; // Username is valid and unique
                }
            }
        }
        return username;
    }

    private static String fillPassword() {
        System.out.print("Enter staff password: ");
        return input.nextLine();
    }

    private static LocalDate fillDateOfBirth() {
        while (true) {
            try {
                System.out.print("Enter birth year: ");
                int year = Integer.parseInt(input.nextLine().trim());

                // Age Validation (21 or older)
                if (year > 2005) {
                    System.out.println("[Access Denied] Staff members must be at least 21 years old (born 2005 or earlier).");
                    continue;
                }
                if (year < 1990) {
                    System.out.println("Invalid year. Please enter a realistic birth year.");
                    continue;
                }

                System.out.print("Enter birth month (1-12): ");
                int month = Integer.parseInt(input.nextLine().trim());
                System.out.print("Enter birth day (1-31): ");
                int day = Integer.parseInt(input.nextLine().trim());

                return LocalDate.of(year, month, day);
            } catch (java.time.DateTimeException e) {
                System.out.println("Invalid date (e.g., February 30th). Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only.");
            }
        }
    }

    private static int fillWorkingHours() {
        while (true) {
            try {
                System.out.print("Enter weekly working hours: ");
                return Integer.parseInt(input.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static exceptions.Job fillJob() {
        while (true) {
            System.out.print("Enter Role (ADMIN or RECEPTIONIST): ");
            try {
                return exceptions.Job.valueOf(input.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid role. Please type exactly ADMIN or RECEPTIONIST.");
            }
        }
    }
}

//  ADMIN SUBCLASS
class Admin extends Staff {
    public Admin(String username, String password, LocalDate dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, exceptions.Job.ADMIN, workingHours);
    }

    public void createAmenity(Amenity a) {
        Database.getAmenities().add(a);
        System.out.println("[Success] Amenity added.");
    }

    public void showAmenity() {
        System.out.println("\n-- Global Amenities --");
        if(Database.getAmenities().isEmpty()) System.out.println("No amenities registered.");
        for (Amenity a : Database.getAmenities()) {
            System.out.println("- " + a.getName());
        }
    }

    public void updateAmenity(String oldN, String newN) {
        for (Amenity a : Database.getAmenities()) {
            if (a.getName().equalsIgnoreCase(oldN)) {
                a.setName(newN);
                System.out.println("[Success] Amenity renamed.");
                return;
            }
        }
        System.out.println("[Error] Amenity not found.");
    }

    public void deleteAmenity(String name) {
        Amenity toRemove = null;
        for (Amenity a : Database.getAmenities()) {
            if (a.getName().equalsIgnoreCase(name)) {
                toRemove = a;
                break;
            }
        }
        if (toRemove != null) {
            Database.getAmenities().remove(toRemove);
            System.out.println("[Success] Amenity removed.");
        } else {
            System.out.println("[Error] Amenity not found.");
        }
    }

    public void createRoomType(int id, String name, double price) {
        Database.getRoomTypes().add(new RoomType(id, name, price));
        System.out.println("[Success] Room Type created.");
    }

    public void readRoomTypes() {
        System.out.println("\n-- Available Room Types --");
        for (RoomType rt : Database.getRoomTypes()) {
            System.out.println("ID: " + rt.getId() + " | " + rt.getName() + " ($" + rt.getPrice() + ")");
        }
    }

    public void updateRoomType(int id, String name, double price) {
        for (RoomType rt : Database.getRoomTypes()) {
            if (rt.getId() == id) {
                rt.setName(name);
                rt.setPrice(price);
                System.out.println("[Success] Room Type updated.");
                return;
            }
        }
        System.out.println("[Error] ID not found.");
    }

    public void deleteRoomType(int id) {
        RoomType toRemove = null;
        for (RoomType rt : Database.getRoomTypes()) {
            if (rt.getId() == id) {
                toRemove = rt;
                break;
            }
        }
        if (toRemove != null) {
            Database.getRoomTypes().remove(toRemove);
            System.out.println("[Success] Room Type deleted.");
        } else {
            System.out.println("[Error] ID not found.");
        }
    }
}

//  RECEPTION SUBCLASS
class Reception extends Staff {
    public Reception(String username, String password, LocalDate dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, exceptions.Job.RECEPTIONIST, workingHours);
    }

    public void checkIn(Room room) {
        System.out.println("Room: " + room.getRoomNumber() + " checked in.");
    }

    public void checkOut(Room room) {
        System.out.println("Room: " + room.getRoomNumber() + " checked out.");
    }
}