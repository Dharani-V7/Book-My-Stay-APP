import java.util.HashMap;
abstract class Room {
    int beds;
    int size;
    double price;

    Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: $" + price);
    }

    abstract String getRoomType();
}

/* Room Types */
class SingleRoom extends Room {
    SingleRoom() {
        super(1, 200, 100);
    }

    String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super(2, 350, 180);
    }

    String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super(3, 600, 400);
    }

    String getRoomType() {
        return "Suite Room";
    }
}

/* Inventory Class (Centralized State Holder) */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // example unavailable
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

/* Search Service */
class RoomSearchService {

    private RoomInventory inventory;

    RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    void searchRooms(Room[] rooms) {

        System.out.println("\nAvailable Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) { // Filter unavailable rooms

                System.out.println("Room Type: " + room.getRoomType());
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("---------------------------");
            }
        }
    }
}

/* Main Class */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Room Search");
        System.out.println("Version 4.0");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Perform search
        RoomSearchService searchService = new RoomSearchService(inventory);
        searchService.searchRooms(rooms);
    }
}