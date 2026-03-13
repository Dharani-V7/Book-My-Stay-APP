import java.util.*;

/* Represents an individual add-on service */
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

/* Manages services attached to reservations */
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Selected Add-On Services:");
        for (AddOnService s : services) {
            System.out.println("- " + s);
        }
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);
        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

/* Main application class */
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.println("===== Book My Stay : Add-On Service Selection =====");

        // reservation id input
        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        int choice;

        do {

            System.out.println("\nSelect Add-On Service");
            System.out.println("1. Breakfast");
            System.out.println("2. Airport Pickup");
            System.out.println("3. Extra Bed");
            System.out.println("4. Spa Access");
            System.out.println("5. Finish Selection");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    manager.addService(reservationId,
                            new AddOnService("Breakfast", 500));
                    break;

                case 2:
                    manager.addService(reservationId,
                            new AddOnService("Airport Pickup", 1200));
                    break;

                case 3:
                    manager.addService(reservationId,
                            new AddOnService("Extra Bed", 800));
                    break;

                case 4:
                    manager.addService(reservationId,
                            new AddOnService("Spa Access", 1500));
                    break;

                case 5:
                    System.out.println("Service selection completed.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);

        System.out.println("\nReservation ID: " + reservationId);

        manager.displayServices(reservationId);

        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("Total Additional Cost: ₹" + totalCost);

        sc.close();
    }
}