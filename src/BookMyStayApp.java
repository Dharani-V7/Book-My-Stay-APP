import java.io.*;
import java.util.*;

/* Reservation class (Serializable) */
class Reservation implements Serializable {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return "ReservationID: " + reservationId +
                ", Guest: " + guestName +
                ", RoomType: " + roomType;
    }
}

/* System State class to persist inventory + bookings */
class SystemState implements Serializable {

    List<Reservation> bookingHistory;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> bookingHistory, Map<String, Integer> inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }
}

/* Persistence Service */
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    /* Save system state to file */
    public void saveState(SystemState state) {

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            oos.writeObject(state);
            oos.close();

            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }
    }

    /* Load system state from file */
    public SystemState loadState() {

        try {
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(FILE_NAME));

            SystemState state = (SystemState) ois.readObject();
            ois.close();

            System.out.println("System state restored successfully.");

            return state;

        } catch (Exception e) {

            System.out.println("No previous state found. Starting fresh.");

            return null;
        }
    }
}

/* Main Class */
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        List<Reservation> bookingHistory;
        Map<String, Integer> inventory;

        /* System startup → restore state */
        SystemState restoredState = persistence.loadState();

        if (restoredState != null) {

            bookingHistory = restoredState.bookingHistory;
            inventory = restoredState.inventory;

        } else {

            bookingHistory = new ArrayList<>();

            inventory = new HashMap<>();
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("===== Book My Stay : Persistence Demo =====");

        System.out.print("Enter Reservation ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Guest Name: ");
        String guest = sc.nextLine();

        System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
        String roomType = sc.nextLine();

        if (inventory.containsKey(roomType) && inventory.get(roomType) > 0) {

            Reservation r = new Reservation(id, guest, roomType);

            bookingHistory.add(r);

            inventory.put(roomType, inventory.get(roomType) - 1);

            System.out.println("Booking Confirmed!");

        } else {

            System.out.println("Booking Failed: Room not available.");
        }

        /* Display bookings */
        System.out.println("\nBooking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        /* Display inventory */
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }

        /* System shutdown → save state */
        SystemState state = new SystemState(bookingHistory, inventory);

        persistence.saveState(state);

        sc.close();
    }
}