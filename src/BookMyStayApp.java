import java.util.*;

/* Custom Exception for Invalid Booking */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/* Validator class to check booking inputs */
class InvalidBookingValidator {

    private Map<String, Integer> roomInventory;

    public InvalidBookingValidator() {

        roomInventory = new HashMap<>();

        // Available rooms
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Suite", 1);
    }

    // Validate booking request
    public void validateBooking(String roomType) throws InvalidBookingException {

        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid Room Type Selected.");
        }

        int available = roomInventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        // Reduce inventory only if valid
        roomInventory.put(roomType, available - 1);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " Rooms Available: " + roomInventory.get(type));
        }
    }
}

/* Main class */
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        InvalidBookingValidator validator = new InvalidBookingValidator();

        System.out.println("===== Book My Stay : Booking Validation =====");

        validator.displayInventory();

        try {

            System.out.print("\nEnter Guest Name: ");
            String guestName = sc.nextLine();

            System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
            String roomType = sc.nextLine();

            // Validate booking request
            validator.validateBooking(roomType);

            System.out.println("\nBooking Successful!");
            System.out.println("Guest: " + guestName);
            System.out.println("Room Type: " + roomType);

        }
        catch (InvalidBookingException e) {

            System.out.println("\nBooking Failed: " + e.getMessage());
        }

        // System continues running safely
        validator.displayInventory();

        sc.close();
    }
}