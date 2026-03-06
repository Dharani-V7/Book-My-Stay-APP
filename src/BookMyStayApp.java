import java.util.*;

/**
 * Book My Stay App
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Demonstrates safe booking confirmation and unique room assignment.
 *
 * @version 6.0
 */

/* Reservation class */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* Booking Request Queue */
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

/* Inventory Service */
class InventoryService {
    private HashMap<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

/* Booking Service for allocation */
class BookingService {

    private InventoryService inventory;

    /* Map room type → allocated room IDs */
    private HashMap<String, Set<String>> allocatedRooms;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
    }

    public void processReservation(Reservation reservation) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) > 0) {

            /* Generate unique room ID */
            String roomId = roomType.replace(" ", "").toUpperCase()
                    + "-" + (int) (Math.random() * 1000);

            /* Ensure room type set exists */
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());

            Set<String> roomSet = allocatedRooms.get(roomType);

            /* Prevent duplicate room IDs */
            while (roomSet.contains(roomId)) {
                roomId = roomType.replace(" ", "").toUpperCase()
                        + "-" + (int) (Math.random() * 1000);
            }

            roomSet.add(roomId);

            /* Update inventory */
            inventory.decreaseAvailability(roomType);

            System.out.println("Reservation Confirmed");
            System.out.println("Guest: " + reservation.getGuestName());
            System.out.println("Room Type: " + roomType);
            System.out.println("Assigned Room ID: " + roomId);
            System.out.println("----------------------------------");

        } else {
            System.out.println("Reservation Failed for "
                    + reservation.getGuestName()
                    + " (No rooms available)");
        }
    }
}

/* Main class */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Room Allocation");
        System.out.println("Version 6.0\n");

        BookingRequestQueue queue = new BookingRequestQueue();
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        /* Add booking requests */
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));

        /* Process queue FIFO */
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r);
        }
    }
}