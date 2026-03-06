import java.util.LinkedList;
import java.util.Queue;

/* Reservation class representing a booking request */
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

/* Booking Request Queue */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    /* Add booking request */
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    /* Display queued requests */
    public void displayRequests() {

        System.out.println("\nCurrent Booking Queue:");

        if (queue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }
}

/* Main Class */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Booking Request Queue");
        System.out.println("Version 5.0\n");

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        /* Simulating guest booking requests */
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        /* Add requests to queue (FIFO order) */
        requestQueue.addRequest(r1);
        requestQueue.addRequest(r2);
        requestQueue.addRequest(r3);

        /* Display queue */
        requestQueue.displayRequests();
    }
}