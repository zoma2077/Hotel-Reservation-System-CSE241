import java.time.LocalDate;
import java.util.Scanner;

public class Guest implements Payable {
    private static final Scanner input = new Scanner(System.in);

    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private double balance;
    private String address;
    private exceptions.Gender gender;
    private String roomPreferences;

    protected Guest(String username, String password, LocalDate dateOfBirth, double balance, String address, exceptions.Gender gender, String roomPreferences) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
        this.address = address;
        this.gender = gender;
        this.roomPreferences = roomPreferences;
    }

    public static Guest register() {
        Guest newUser = new Guest(fillUsername(), fillPassword(), fillDateOfBirth(), fillBalance(), fillAddress(), fillGender(), fillRoomPreferences());
        Database.getGuests().add(newUser); // Updated to use getter
        return newUser;
    }

    public static Guest login() {
        System.out.print("Enter username: ");
        String username = input.nextLine().trim();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        for (Guest g : Database.getGuests()) { // Updated to use getter
            if (g.username.equals(username) && g.password.equals(password)) {
                System.out.println("Login successful!");
                return g;
            }
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    private static String fillUsername() {
        String username;
        while (true) {
            System.out.print("Enter your username: ");
            username = input.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
            } else if (username.length() < 3) {
                System.out.println("Username must be at least 3 characters.");
            } else if (!username.matches("[a-zA-Z0-9_]+")) {
                System.out.println("Username can only contain letters, numbers, and underscore.");
            } else {
                //  Uniqueness Validation
                boolean exists = false;
                for (Guest g : Database.getGuests()) {
                    if (g.getUsername().equalsIgnoreCase(username)) {
                        exists = true;
                        break; // Stop looping once we find a match
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
        String password;
        while (true) {
            System.out.print("Enter your password: ");
            password = input.nextLine();
            if (!isValidPassword(password)) {
                System.out.println("Password must be at least 8 characters and include:");
                System.out.println("- Uppercase letter\n- Lowercase letter\n- Number\n- Special character (!@#$%^&*)");
            } else break;
        }
        return password;
    }

    private static boolean isValidPassword(String password) {
        if (password.length() < 8) return false;
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if ("_!@#$%^&*".contains(String.valueOf(c))) hasSpecial = true;
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    private static LocalDate fillDateOfBirth() {
        int day, month, year;
        while (true) {
            try {
                System.out.print("Enter birth year: ");
                year = Integer.parseInt(input.nextLine().trim());

                // Age Validation: Must be 21 or older
                if (year > 2005) {
                    System.out.println("Registration Denied..... Guests must be at least 21 years old to create an account.");
                    continue;
                }


                if (year < 1940) {
                    System.out.println("Invalid year. Please enter a realistic birth year.");
                    continue;
                }

                System.out.print("Enter birth month (1-12): ");
                month = Integer.parseInt(input.nextLine().trim());

                System.out.print("Enter birth day (1-31): ");
                day = Integer.parseInt(input.nextLine().trim());

                return LocalDate.of(year, month, day);
            } catch (java.time.DateTimeException e) {
                // Catches errors like Feb 30th
                System.out.println("Invalid date format. Please check the month and day.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only.");
            }
        }
    }

    private static double fillBalance() {
        double balance = 0;
        while (true) {
            try {
                System.out.print("Please enter your balance: ");
                balance = Double.parseDouble(input.nextLine());
                if (balance < 0) { System.out.println("Balance cannot be negative."); continue; }
                return balance;
            } catch (Exception e) { System.out.println("Please enter numbers only."); }
        }
    }


    private static String fillAddress() {
        System.out.println("Enter your country:"); String country = input.nextLine();
        System.out.println("Enter your province:"); String province = input.nextLine();
        System.out.println("Enter your city:"); String city = input.nextLine();
        System.out.println("Enter your street name:"); String street = input.nextLine();
        return (country + "-" + province + "-" + city + "-" + street);
    }

    private static exceptions.Gender fillGender() {
        while (true) {
            System.out.println("Enter your gender (MALE/FEMALE):");
            try { return exceptions.Gender.valueOf(input.nextLine().trim().toUpperCase()); }// ignores it is upper capped or not
            catch (IllegalArgumentException e) { System.out.println("Invalid input. Please enter MALE or FEMALE."); }
        }
    }

    private static String fillRoomPreferences() {
        while (true) {
            System.out.print("Enter your room preferences: ");
            String pref = input.nextLine().trim();
            if (pref.isEmpty()) System.out.println("Preference cannot be empty.");
            else return pref;
        }
    }
    public String getUsername() { return this.username; }

    @Override
    public void processPayment(double amount, exceptions.PaymentMethod method) {

    }
}






