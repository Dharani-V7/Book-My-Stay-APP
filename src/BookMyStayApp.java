import java.util.*;

/* Represents a Reservation */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean isCancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        isCancelled = true;
    }

    public String toString() {
        return "ReservationID: " + reservationId +
                ", Guest: " + guestName +
                ", RoomType: " + roomType +
                ", RoomID: " + roomId +
                ", Status: " + (isCancelled ? "Cancelled" : "Confirmed");
    }
}

/* Cancellation Service */
class CancellationService {

    private Map<String, Reservation> reservations;
    private Map<String, Integer> inventory;
    private Stack<String> rollbackStack;

    public CancellationService() {

        reservations = new HashMap<>();
        rollbackStack = new Stack<>();

        inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    /* Confirm booking */
    public void confirmBooking(String id, String guest, String type) {

        if (!inventory.containsKey(type) || inventory.get(type) <= 0) {
            System.out.println("Booking Failed: No rooms available for " + type);
            return;
        }

        String roomId = type.substring(0,1).toUpperCase() + (inventory.get(type));

        inventory.put(type, inventory.get(type) - 1);

        Reservation r = new Reservation(id, guest, type, roomId);
        reservations.put(id, r);

        System.out.println("Booking Confirmed: " + r);
    }

    /* Cancel booking with rollback */
    public void cancelBooking(String reservationId) {

        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        Reservation r = reservations.get(reservationId);

        if (r.isCancelled()) {
            System.out.println("Cancellation Failed: Reservation already cancelled.");
            return;
        }

        // LIFO rollback
        rollbackStack.push(r.getRoomId());

        // Restore inventory
        inventory.put(r.getRoomType(), inventory.get(r.getRoomType()) + 1);

        // Mark cancelled
        r.cancel();

        System.out.println("Cancellation Successful for Reservation: " + reservationId);
        System.out.println("Room Released: " + rollbackStack.peek());
    }

    /* Display inventory */
    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
    }

    /* Display reservations */
    public void displayReservations() {

        System.out.println("\nReservation Records:");
        for (Reservation r : reservations.values()) {
            System.out.println(r);
        }
    }
}

/* Main Class */
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CancellationService service = new CancellationService();

        System.out.println("===== Book My Stay : Booking Cancellation =====");

        service.displayInventory();

        System.out.print("\nEnter Reservation ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Guest Name: ");
        String guest = sc.nextLine();

        System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
        String type = sc.nextLine();

        service.confirmBooking(id, guest, type);

        service.displayInventory();

        System.out.print("\nDo you want to cancel this booking? (yes/no): ");
        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            service.cancelBooking(id);
        }

        service.displayInventory();

        service.displayReservations();

        sc.close();
    }
}