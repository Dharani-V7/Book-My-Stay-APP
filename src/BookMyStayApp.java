import java.util.*;

/* Represents a reservation */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Price: ₹" + price;
    }
}

/* Maintains booking history */
class BookingHistory {

    // List to store confirmed reservations
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Retrieve reservation history
    public List<Reservation> getReservations() {
        return reservations;
    }

    // Display booking history
    public void displayHistory() {
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\nBooking History:");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}

/* Generates reports from booking history */
class BookingReportService {

    public void generateSummaryReport(List<Reservation> reservations) {

        System.out.println("\n===== Booking Summary Report =====");

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getPrice();
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

/* Main class */
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.println("===== Book My Stay : Booking History & Reporting =====");

        System.out.print("Enter number of confirmed bookings to store: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nEnter details for booking " + i);

            System.out.print("Reservation ID: ");
            String id = sc.nextLine();

            System.out.print("Guest Name: ");
            String guest = sc.nextLine();

            System.out.print("Room Type: ");
            String room = sc.nextLine();

            System.out.print("Price: ");
            double price = sc.nextDouble();
            sc.nextLine();

            Reservation reservation = new Reservation(id, guest, room, price);

            // Add confirmed reservation to history
            history.addReservation(reservation);
        }

        // Admin views booking history
        history.displayHistory();

        // Generate report
        reportService.generateSummaryReport(history.getReservations());

        sc.close();
    }
}