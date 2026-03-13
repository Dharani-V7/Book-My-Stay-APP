import java.util.*;

/* Booking Request class */
class BookingRequest {

    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
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

/* Concurrent Booking Processor */
class ConcurrentBookingProcessor {

    private Queue<BookingRequest> bookingQueue;
    private Map<String, Integer> inventory;

    public ConcurrentBookingProcessor() {

        bookingQueue = new LinkedList<>();

        inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    /* Add booking request to queue */
    public synchronized void addBookingRequest(BookingRequest request) {

        bookingQueue.add(request);
        System.out.println("Request Added: " + request.getGuestName() +
                " requested " + request.getRoomType());
    }

    /* Process booking requests safely */
    public synchronized void processBooking() {

        if (bookingQueue.isEmpty()) {
            return;
        }

        BookingRequest request = bookingQueue.poll();

        String roomType = request.getRoomType();

        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type requested by " + request.getGuestName());
            return;
        }

        int available = inventory.get(roomType);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            System.out.println("Booking Confirmed for " +
                    request.getGuestName() +
                    " | Room Type: " + roomType);
        }
        else {

            System.out.println("Booking Failed for " +
                    request.getGuestName() +
                    " | No rooms available for " + roomType);
        }
    }

    public void displayInventory() {

        System.out.println("\nFinal Inventory State:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
    }
}

/* Worker Thread */
class BookingWorker extends Thread {

    private ConcurrentBookingProcessor processor;

    public BookingWorker(ConcurrentBookingProcessor processor) {
        this.processor = processor;
    }

    public void run() {

        processor.processBooking();
    }
}

/* Main Class */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay : Concurrent Booking Simulation =====");

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();

        /* Simulated guest requests */
        processor.addBookingRequest(new BookingRequest("Ram", "Deluxe"));
        processor.addBookingRequest(new BookingRequest("Arun", "Deluxe"));
        processor.addBookingRequest(new BookingRequest("Kiran", "Deluxe"));
        processor.addBookingRequest(new BookingRequest("Ravi", "Suite"));
        processor.addBookingRequest(new BookingRequest("Meena", "Suite"));

        /* Multiple threads processing requests */
        List<Thread> workers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Thread worker = new BookingWorker(processor);
            workers.add(worker);
            worker.start();
        }

        /* Wait for all threads to finish */
        for (Thread t : workers) {
            try {
                t.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        processor.displayInventory();
    }
}