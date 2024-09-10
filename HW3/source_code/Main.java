import java.util.Scanner;

/**
 * Main class for managing an electronic device inventory. Offers a menu to add, remove, update, and list devices,
 * among other functionalities like sorting by price, calculating total inventory value, restocking, and exporting reports.
 * Utilizes the Inventory class for operations, each with its specific time complexity.
 */

public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory(); // Create inventory object
        Scanner scanner = new Scanner(System.in); // Scanner for user input

        while (true) {
            // Display menu options
            System.out.println("\nWelcome to the Electronics Inventory Management System!");
            System.out.println("Please select an option:");
            System.out.println("1. Add a new device");
            System.out.println("2. Remove a device");
            System.out.println("3. Update device details");
            System.out.println("4. List all devices");
            System.out.println("5. Find the cheapest device");
            System.out.println("6. Sort devices by price");
            System.out.println("7. Calculate total inventory value");
            System.out.println("8. Restock a device");
            System.out.println("9. Export inventory report");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");

            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.printf("\"%s\" is not a valid number. Please enter a number between 1 and 9: ", input);
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    inventory.addDeviceFromUserInput(); //The time complexity = O(1) 
                    break; 
                case 2:
                    inventory.removeDeviceFromUserInput(); //The time complexity = O(n*m)
                    break;
                case 3:
                    inventory.updateDeviceDetails(); //The time complexity = O(n*m)
                    break;                
                case 4:
                    inventory.listDevices(); //The time complexity = O(n*m)
                    break;
                case 5:
                    inventory.findCheapestDevice(); //The time complexity = O(n)
                    break;
                case 6:
                    inventory.sortDevicesByPrice(); //The time complexity = O(nmlog(m))
                    inventory.listDevices(); //The time complexity = O(n*m)
                    break;
                case 7:
                    inventory.calculateTotalInventoryValue(); //The time complexity = O(n)
                    break;
                case 8:
                    inventory.restockaDevice(); //The time complexity = O(n*m)
                    break;                                            
                case 9:
                    inventory.printInventoryReport(); //The time complexity = O(n)
                    inventory.printInventoryReport("Report.txt");
                    break;
                case 0:
                    System.out.println("Exiting...");
                    scanner.close(); 
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}