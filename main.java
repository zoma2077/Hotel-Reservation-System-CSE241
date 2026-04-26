import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Database.initialize();
        Scanner input = new Scanner(System.in);
        System.out.println("User 1: Ahmed | Pass: Ahmed@2026");
        System.out.println("User 2: Sara | Pass: Sara@2026\n");
        System.out.println("Staff 1: Saif | Pass: Admin@2026");
        System.out.println("Staff 2: Ellie | Pass: Staff@2026\n");
        while (true) {
            System.out.println("\n Welcome to the Hotel Reservation System ");
            System.out.println("1. Guest Register");
            System.out.println("2. Guest Login");
            System.out.println("3. Staff Register");
            System.out.println("4. Staff Login");
            System.out.println("5. Exit");
            System.out.print("Choice: ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (choice == 1) {
                Guest nG = Guest.register();
                System.out.println("Registration successful for " + nG.getUsername() + "!");
            }
            else if (choice == 2) {
                Guest loggedIn = Guest.login();
                if (loggedIn != null) {
                    guestDashboard(loggedIn, input);
                }
            }
            else if (choice == 3) {
                Staff newStaff = Staff.register();
                System.out.println("Success! " + newStaff.getUsername() + " has been registered as " + newStaff.getJob() + ".");
            }
            else if (choice == 4) {
                staffLoginFlow(input);
            }
            else if (choice == 5) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please select a valid number.");
            }
        }
    }

    private static void staffLoginFlow(Scanner input) {
        //  Ask for the role
        System.out.println("\nSelect Login Portal:");
        System.out.println("1. Admin");
        System.out.println("2. Receptionist");
        System.out.print("Choice: ");
        String roleChoice = input.nextLine().trim();

        if (!roleChoice.equals("1") && !roleChoice.equals("2")) {
            System.out.println("Invalid choice. Returning to main menu.");
            return;
        }

        //  Ask for credentials
        System.out.print("Staff Username: ");
        String user = input.nextLine();
        System.out.print("Staff Password: ");
        String pass = input.nextLine();

        Staff staff = Database.staffLogin(user, pass);

        //  Verify credentials AND the correct role
        if (staff != null) {
            if (roleChoice.equals("1") && staff instanceof Admin) {
                System.out.println("\nWelcome to the Admin Portal, " + staff.getUsername());
                adminDashboard((Admin) staff, input);
            } else if (roleChoice.equals("2") && staff instanceof Reception) {
                System.out.println("\nWelcome to the Receptionist Portal, " + staff.getUsername());
                receptionDashboard((Reception) staff, input);
            } else {
                // If an Admin tries to log in as Receptionist (or vice versa)
                System.out.println("\nAccess Denied.... Your account does not have permission for this portal.");
            }
        } else {
            System.out.println("\nInvalid Staff Credentials.");
        }
    }

    // ADMIN DASHBOARD
    private static void adminDashboard(Admin admin, Scanner input) {
        boolean inStaffPanel = true;

        while (inStaffPanel) {
            System.out.println("\n --- ADMIN  CONTROL --- ");
            System.out.println("1. Manage Amenities");
            System.out.println("2. Manage Room Types");
            System.out.println("3. Logout");
            System.out.print("Select Category: ");

            String category = input.nextLine().trim();

            switch (category) {
                case "1":
                    manageAmenitiesFlow(admin, input);
                    break;
                case "2":
                    manageRoomTypesFlow(admin, input);
                    break;
                case "3":
                    inStaffPanel = false;
                    System.out.println("Logging out Admin...");
                    break;
                default:
                    System.out.println("Invalid category selection.");
            }
        }
    }

    private static void manageAmenitiesFlow(Admin admin, Scanner input) {
        boolean active = true;
        while (active) {
            System.out.println("\n --- AMENITY MANAGEMENT --- ");
            System.out.println("1. List All  2. Add New  3. Update  4. Remove  5. Back");
            System.out.print("Action: ");
            int action;
            try { action = Integer.parseInt(input.nextLine().trim()); } catch(Exception e) { continue; }

            switch (action) {
                case 1: admin.showAmenity(); break;
                case 2:
                    System.out.print("New Amenity Name: ");
                    admin.createAmenity(new Amenity(input.nextLine()));
                    break;
                case 3:
                    System.out.print("Old Name: "); String oldA = input.nextLine();
                    System.out.print("New Name: "); String newA = input.nextLine();
                    admin.updateAmenity(oldA, newA);
                    break;
                case 4:
                    System.out.print("Name to Delete: ");
                    admin.deleteAmenity(input.nextLine());
                    break;
                case 5: active = false; break;
            }
        }
    }

    private static void manageRoomTypesFlow(Admin admin, Scanner input) {
        boolean active = true;
        while (active) {
            System.out.println("\n --- ROOM TYPE MANAGEMENT --- ");
            System.out.println("1. List All  2. Create  3. Update  4. Remove  5. Back");
            System.out.print("Action: ");
            int action;
            try { action = Integer.parseInt(input.nextLine().trim()); } catch(Exception e) { continue; }

            switch (action) {
                case 1: admin.readRoomTypes(); break;
                case 2:
                    System.out.print("ID: "); int id = Integer.parseInt(input.nextLine());
                    System.out.print("Name: "); String name = input.nextLine();
                    System.out.print("Price: "); double price = Double.parseDouble(input.nextLine());
                    admin.createRoomType(id, name, price);
                    break;
                case 3:
                    System.out.print("ID to Update: "); int uId = Integer.parseInt(input.nextLine());
                    System.out.print("New Name: "); String uName = input.nextLine();
                    System.out.print("New Price: "); double uPrice = Double.parseDouble(input.nextLine());
                    admin.updateRoomType(uId, uName, uPrice);
                    break;
                case 4:
                    System.out.print("ID to Remove: "); int dId = Integer.parseInt(input.nextLine());
                    admin.deleteRoomType(dId);
                    break;
                case 5: active = false; break;
            }
        }
    }

    // --- RECEPTION DASHBOARD ---
    private static void receptionDashboard(Reception rec, Scanner input) {
        boolean active = true;
        while (active) {
            System.out.println("\n --- RECEPTION DASHBOARD --- ");
            System.out.println("1. Check-In\n2. Check-Out\n3. Logout");
            System.out.print("Choice: ");
            int choice;
            try { choice = Integer.parseInt(input.nextLine().trim()); } catch(Exception e) { continue; }

            if (choice == 1) {
                System.out.print("Room Number: ");
                int rNum = Integer.parseInt(input.nextLine().trim());
                Room r = Database.getRoomByNumber(rNum);
                if (r != null) rec.checkIn(r);
                else System.out.println("Room not found.");
            } else if (choice == 3) {
                active = false;
            }
        }
    }

    // --- GUEST DASHBOARD ---
    // --- GUEST DASHBOARD ---
    private static void guestDashboard(Guest loggedIn, Scanner input) {
        boolean active = true;
        boolean showMenu = true;

        while (active) {
            if (showMenu) {
                System.out.println("\n --- GUEST DASHBOARD --- ");
                System.out.println("1. View Rooms  2. Book Room  3. My Reservation");
                System.out.println("4. Cancel      5. Pay Bill   6. Logout");
                System.out.println("7. SHOW MENU AGAIN");
                showMenu = false;
            }

            System.out.print("\nEnter Choice (7 for menu): ");
            int gChoice;
            try {
                gChoice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            switch (gChoice) {
                case 1:
                    viewAvailableRooms();
                    break;
                case 2:
                    viewAvailableRooms();
                    System.out.print("Room #: ");
                    try {
                        int rn = Integer.parseInt(input.nextLine().trim());
                        System.out.print("Days: ");
                        int dy = Integer.parseInt(input.nextLine().trim());
                        Room r = Database.getRoomByNumber(rn);
                        if (r != null) loggedIn.makeReservation(r, dy);
                        else System.out.println("Room not found!");
                    } catch (Exception e) { System.out.println("Invalid input."); }
                    break;
                case 3:
                    loggedIn.viewMyReservations();
                    break;
                case 4:
                    System.out.print("Enter Room # to cancel: ");
                    try {
                        int rNum = Integer.parseInt(input.nextLine().trim());
                        loggedIn.cancelReservation(rNum);
                    } catch (Exception e) { System.out.println("Invalid input."); }
                    break;
                case 5:
                    System.out.print("Amount to pay: $");
                    try {
                        double amt = Double.parseDouble(input.nextLine().trim());
                        loggedIn.payInvoice(amt);
                    } catch (Exception e) { System.out.println("Invalid amount."); }
                    break;
                case 6:
                    active = false;
                    System.out.println("Logging out...");
                    break;
                case 7:
                    showMenu = true;
                    break;
                default:
                    System.out.println("Invalid Choice.");
                    break;
            }
        }
    }

    private static void viewAvailableRooms() {
        System.out.println("\n Available Rooms ");
        boolean found = false;
        for (Room r : Database.getRooms()) {
            if (r.isAvailable()) {
                System.out.println("Room " + r.getRoomNumber() + " [" + r.getRoomType().getName() + "]");
                System.out.println("   Price: $" + r.getRoomType().getPrice() + "/night");
                System.out.print("   Amenities: ");
                if (r.getAmenities().isEmpty()) {
                    System.out.println("Standard setup.");
                } else {
                    for (int i = 0; i < r.getAmenities().size(); i++) {
                        System.out.print(r.getAmenities().get(i).getName() + (i == r.getAmenities().size() - 1 ? "" : ", "));
                    }
                    System.out.println();
                }
                System.out.println("-----------------------");
                found = true;
            }
        }
        if (!found) System.out.println("No rooms currently available.");
    }
}