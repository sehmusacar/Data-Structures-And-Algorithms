import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.StringWriter;

/**
 * Manages an inventory of electronic devices, categorized in a list.
 * Allows adding devices based on user inputs via {@code addDeviceFromUserInput()} method.
 * 
 * <p>Time complexity for adding a device: O(1).</p>
 */

public class Inventory {
    private LinkedList<ArrayList<Device>> deviceLists;
    Scanner scanner = new Scanner(System.in);

    public Inventory() {
        deviceLists = new LinkedList<>();
    }

    /**
     * Adds a device to the inventory based on user input. Users are prompted to specify device details such as category, name, price, and quantity. 
     * The process allows exiting at any point by entering 'q'. Validates input before adding the device.
     * 
     * Time complexity: O(1), assuming adding the device to the inventory is a constant time operation.
     */

    public void addDeviceFromUserInput() { //The time complexity = O(1) 
        String category = "";
        while (true) {
            // Prompt the user to enter a category name or press 'q' to go back
            System.out.print("Enter category name (Tablet, Phone, Headphone, Computer, TV or press 'q' to go back): ");
            category = scanner.nextLine().trim().toLowerCase();
            
            // Check if the user wants to go back to the main menu
            if (category.equalsIgnoreCase("q")) {
                break; // Exit the loop, effectively going back to the main menu
            }
    
            // Check if the entered category is valid
            if (category.equals("tablet") || category.equals("phone") || category.equals("headphone") || category.equals("computer") || category.equals("tv")) {
                // Prompt for the device name
                System.out.print("Enter device name: ");
                String name = scanner.nextLine().trim();
    
                // Request the price, ensure it's a positive number
                double price = -1.0;
                while (price <= 0) {
                    System.out.print("Enter price (a positive number or press 'q' to go back): ");
                    String priceInput = scanner.nextLine().trim();
                    if (priceInput.equalsIgnoreCase("q")) {
                        break; // Allow the user to go back to the main menu
                    }
                    try {
                        // Remove any non-numeric characters (except decimal point) before parsing
                        price = Double.parseDouble(priceInput.replaceAll("[^\\d.]+", ""));
                        if (price <= 0) {
                            System.out.println("Please enter a positive number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
    
                // If 'q' was entered for price, break out of the loop
                if (price <= 0) {
                    break; 
                }
    
                // Request the quantity, ensure it's a positive number
                int quantity = -1;
                while (quantity <= 0) {
                    System.out.print("Enter quantity (a positive number or press 'q' to go back): ");
                    String quantityInput = scanner.nextLine().trim();
                    if (quantityInput.equalsIgnoreCase("q")) {
                        break; // Allow the user to go back to the main menu
                    }
                    try {
                        quantity = Integer.parseInt(quantityInput);
                        if (quantity <= 0) {
                            System.out.println("Please enter a positive number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
    
                // If 'q' was entered for quantity, break out of the loop
                if (quantity <= 0) {
                    break; 
                }
    
                // Create and add the device
                Device device = createDevice(category, name, price, quantity);
                if (device != null) {
                    addDevice(device);
                    System.out.printf("%s, %s, $%.2f, %d amount added...\n", category, name, price, quantity);
                } else {
                    System.out.println("An error occurred. No device added.");
                }
                break; // Exit after adding the device
            } else {
                // Inform the user of invalid category and prompt again
                System.out.println("Invalid category. Please enter a valid category name from the list.");
            }
        }
    }

    /**
     * Adds a given device to the inventory. If the device's category already exists, 
     * it is added to that category's list. Otherwise, a new list is created for this category.
     * 
     * <p>Time complexity: O(N) where N is the number of device categories. In the worst case,
     * this method iterates through all categories to find a match or to add a new category.</p>
     *
     * @param device the device to be added to the inventory
     */

    public void addDevice(Device device) { //The time complexity = O(1) 
        boolean found = false; // Flag to check if a matching category list is found
        
        // Iterate over each device list to find a matching category
        for (ArrayList<Device> deviceList : deviceLists) {
            // Check if the list is not empty and the category matches the device's category
            if (!deviceList.isEmpty() && deviceList.get(0).getCategory().equals(device.getCategory())) {
                deviceList.add(device); // Add the device to the matching list
                found = true; // Mark as found
                break; // Exit the loop as we've added the device
            }
        }
        
        // If no matching category list is found
        if (!found) {
            ArrayList<Device> newDeviceList = new ArrayList<>(); // Create a new list
            newDeviceList.add(device); // Add the device to this new list
            deviceLists.add(newDeviceList); // Add the new list to the collection of lists
        }
    }
    
    /**
     * Creates a new device based on the specified category, name, price, and quantity.
     * Supports creation of devices in categories: phone, computer, tv, headphone, and tablet.
     * 
     * <p>Time complexity: O(1), as the operation does not depend on the size of the input 
     * and consists of a constant-time switch-case statement.</p>
     *
     * @param category the category of the device
     * @param name the name of the device
     * @param price the price of the device
     * @param quantity the quantity of the device
     * @return a new Device instance of the specified category, or {@code null} if the category is not recognized
     */

    private static Device createDevice(String category, String name, double price, int quantity) { //The time complexity = O(1) 
        switch (category.toLowerCase()) {
            case "phone":
                return new Phone(name, price, quantity);
            case "computer":
                return new Computer(name, price, quantity);
            case "tv":
                return new TV(name, price, quantity);
            case "headphone":
                return new Headphone(name, price, quantity);
            case "tablet":
                return new Tablet(name, price, quantity);
            default:
                return null;
        }
    }

    /**
     * Prompts user to remove a device by name. Continues until a device is removed or 'q' is entered to quit.
     * 
     * Time complexity: O(n*m), where n is the number of categories and m is the average number of devices per category.
     *
     * @param deviceName the name of the device to remove.
     */
    
    public void removeDeviceFromUserInput() { //The time complexity = O(n*m)
        while (true) {
            // Prompt the user to enter the name of the device they wish to remove or 'q' to go back
            System.out.print("Enter the name of the device to remove (or press 'q' to go back): ");
            String deviceToRemove = scanner.nextLine().trim();
        
            // Check if the user has chosen to go back to the main menu
            if (deviceToRemove.equalsIgnoreCase("q")) {
                break; // Exit the loop, effectively returning to the previous menu or main menu
            }
        
            // Check if the specified device exists in the inventory
            if (deviceExists(deviceToRemove)) {
                // If the device exists, remove it
                removeDevice(deviceToRemove);
                // Confirm to the user that the device has been successfully removed
                System.out.println(deviceToRemove + " has been removed from the inventory.");
                break; // Exit the loop after successful removal
            } else {
                // If no device with the given name exists, inform the user and prompt again
                System.out.println("Device not found. Please try again or press 'q' to go back.");
            }
        }
    }

    /**
     * Removes a device by name across all categories. If the device exists and is removed, returns true; otherwise, returns false.
     * Iterates through each category and device list to find and remove the specified device.
     * 
     * Time complexity: O(n*m), where n is the number of device categories and m is the average number of devices per category.
     *
     * @param deviceName the name of the device to be removed from the inventory.
     * @return true if the device was found and removed, false if the device could not be found.
     */
    
    public boolean removeDevice(String deviceName) { //The time complexity = O(n*m)
        for (ArrayList<Device> deviceList : deviceLists) {
            for (int i = 0; i < deviceList.size(); i++) {
                if (deviceList.get(i).getName().equalsIgnoreCase(deviceName)) {
                    deviceList.remove(i); // Remove device
                    return true; // Device found and removed
                }
            }
        }
        return false; // Device not found
    }

    /**
     * Updates price and/or quantity details of a specified device. Users are prompted for the device name, new price, and new quantity.
     * Skips the update for either price or quantity if the input is -1, and exits on 'q'.
     * 
     * Time complexity: O(n*m), where n is the number of device categories and m is the average number of devices per category.
     * 
     * @param deviceName The name of the device to be updated.
     * @param newPrice The new price to set for the device. If the input is -1, the price update is skipped.
     * @param newQuantity The new quantity to set for the device. If the input is -1, the quantity update is skipped.
     */

    public void updateDeviceDetails(){ //The time complexity = O(n*m)
        while (true) {
            // Prompt the user to enter the name of the device they want to update or 'q' to exit
            System.out.print("Enter the name of the device to update (or press 'q to go back): ");
            String deviceToUpdate = scanner.nextLine().trim();

            if (deviceToUpdate.equalsIgnoreCase("q")) {
                return;
            }
            if (!deviceExists(deviceToUpdate)) {
                System.out.println("No such device found. Please try again.");
                return;
            }

            Double newPrice = null;
            String priceInput; // To store user input for price
            while (newPrice == null) {
                System.out.print("Enter new price (or -1 to skip): ");
                priceInput = scanner.nextLine().trim().replaceAll("[^\\d.-]", ""); // Remove non-numeric characters except dot and minus

                if (priceInput.equalsIgnoreCase("q")) {
                    return;
                }
                // Check for valid input
                try {
                    newPrice = Double.parseDouble(priceInput);
                    if (newPrice < -1) {
                        System.out.println("Price cannot be negative (other than -1 to skip). Please enter a positive number or -1 to skip.");
                        newPrice = null; // Reset to null to re-prompt the user
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    newPrice = null; // Reset to null to re-prompt the user
                }
            }
            
            Integer newQuantity = null;
            String quantityInput = ""; // To store user input for quantity
            while (newQuantity == null) {
                System.out.print("Enter new quantity (or -1 to skip): ");
                quantityInput = scanner.nextLine().trim();
                // Check for 'q' to exit
                if (quantityInput.equalsIgnoreCase("q")) {
                    break;
                }
                // Attempt to parse and validate new quantity
                try {
                    newQuantity = Integer.parseInt(quantityInput);
                    // Validate quantity is positive or -1 to skip
                    if (newQuantity < -1) {
                        System.out.println("Quantity cannot be negative (other than -1 to skip). Please enter a positive number or -1 to skip.");
                        newQuantity = null; // Ensure loop continues if input is invalid
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }
            
            // Exit if user inputs 'q' after quantity prompt
            if (quantityInput.equalsIgnoreCase("q")) {
                break;
            }
            
            // Proceed to update device if valid inputs are received
            boolean updateResult = updateDeviceDetails(deviceToUpdate, newPrice.equals(-1.0) ? null : newPrice, newQuantity.equals(-1) ? null : newQuantity);
                if (updateResult) {
                    System.out.printf("%s details updated: ", deviceToUpdate);
                    if (newPrice != null && !newPrice.equals(-1.0)) {
                        System.out.printf("Price - $%.2f, ", newPrice);
                    }
                    if (newQuantity != null && !newQuantity.equals(-1)) {
                        System.out.printf("Quantity - %d", newQuantity);
                    }
                    System.out.println();
                } else {
                    System.out.println("An error occurred. Device could not be updated.");
                }

            break; // Exit loop after one update cycle or if 'q' is entered
        }
    }
    
    /**
     * Updates the price and/or quantity for a specified device by name. If new values are provided, updates are applied.
     * Returns true if updates were made, false if the device was not found.
     * 
     * Time complexity: O(n*m), with n being categories and m the average devices per category.
     */

    public boolean updateDeviceDetails(String deviceName, Double newPrice, Integer newQuantity) {
        // Updates price and/or quantity of a device if found
        boolean updated = false;
        for (ArrayList<Device> deviceList : deviceLists) {
            for (Device device : deviceList) {
                if (device.getName().equalsIgnoreCase(deviceName)) {
                    if (newPrice != null && !newPrice.equals(-1.0)) { // Check for valid price
                        device.setPrice(newPrice);
                        updated = true;
                    }
                    if (newQuantity != null && !newQuantity.equals(-1)) { // Check for valid quantity
                        device.setQuantity(newQuantity);
                        updated = true;
                    }
                    return updated; // Device updated successfully
                }
            }
        }
        return false; // Device not found
    } 

    /**
     * Lists all devices in the inventory by category, including name, price, and quantity.
     * Displays a message if the inventory is empty.
     * 
     * Time complexity: O(n*m), where n is the number of categories and m is the average number of devices per category.
     */

    public void listDevices() { //The time complexity = O(n*m)
        if (isEmpty()) {
            System.out.println("No devices in inventory."); // Inventory check
        } else {
            for (ArrayList<Device> deviceList : deviceLists) { // Loop through lists
                for (Device device : deviceList) { // Print device details
                    System.out.println("Category: " + device.getCategory() + ", Name: " + device.getName() +
                            ", Price: " + device.getPrice() + "$, Quantity: " + device.getQuantity());
                }
            }
        }
    }
    
    /**
     * Finds the cheapest device in the inventory and prints its details. 
     * 
     * Time complexity: O(n), where n is the total number of devices in the inventory.
     */

    public void findCheapestDevice() {  //The time complexity = O(n)
        Device cheapestDevice = null; // Track cheapest device
        for (ArrayList<Device> deviceList : deviceLists) { // Loop through lists
            for (Device device : deviceList) { // Find cheapest device
                if (cheapestDevice == null || device.getPrice() < cheapestDevice.getPrice()) {
                    cheapestDevice = device;
                }
            }
        }
        if (cheapestDevice != null) { // Print cheapest device details
            System.out.println("The cheapest device is: Category: " + cheapestDevice.getCategory() +
                    ", Name: " + cheapestDevice.getName() + ", Price: " + cheapestDevice.getPrice() +
                    "$, Quantity: " + cheapestDevice.getQuantity());
        } else {
            System.out.println("No devices in inventory."); // No devices case
        }
    }

    /**
     * Sorts all devices in each category by price in ascending order.
     * 
     * Time complexity: O(nmlog(m)), where n is the number of categories and m is the size of each category.
     */

    public void sortDevicesByPrice() { //The time complexity = O(nmlog(m))
        for (ArrayList<Device> deviceList : deviceLists) { // Loop through device lists
            Collections.sort(deviceList, new Comparator<Device>() { // Sort each list by price
                @Override
                public int compare(Device d1, Device d2) {
                    return Double.compare(d1.getPrice(), d2.getPrice());
                }
            });
        }
        System.out.println("Devices sorted by price."); // Confirmation message
    }
    
    /**
     * Calculates the total value of the inventory, summing up the product of price and quantity for each device.
     * 
     * Time complexity: O(n), where n is the total number of devices across all categories.
     */
    public void calculateTotalInventoryValue() { //The time complexity = O(n)
        if (isEmpty()) {
            System.out.println("No devices in inventory."); // Check inventory status
        } else {
            double totalValue = 0; // Initialize total value
            for (ArrayList<Device> deviceList : deviceLists) { // Loop through device lists
                for (Device device : deviceList) { // Sum up device values
                    totalValue += device.getPrice() * device.getQuantity();
                }
            }
            System.out.printf("Total inventory value: $%.2f%n", totalValue); // Print total value
        }
    }

    /**
     * Checks if the inventory is empty, meaning no devices are present in any category.
     * 
     * Time complexity: O(m), where m is the number of device categories. The method checks each category for devices.
     * 
     * @return true if the inventory is empty, false otherwise.
     */
    
    public boolean isEmpty() { //The time complexity = O(m)
        for (ArrayList<Device> deviceList : deviceLists) { // Check each list
            if (!deviceList.isEmpty()) {
                return false; // Not empty if any list has devices
            }
        }
        return true; // Empty if all lists are empty
    }

    /**
     * Restocks or deducts stock for a specified device by name. If the device is found, its stock is updated based on the provided quantity.
     * 
     * Time complexity: O(n*m), where n is the number of device categories and m is the average number of devices per category.
     * 
     * @param deviceName the name of the device to restock or deduct stock.
     * @param quantity the amount to add or subtract from the device's stock.
     * @param addStock true to add stock, false to deduct stock.
     */

    public void restockDevice(String deviceName, int quantity, boolean addStock) { //The time complexity = O(n*m)
        for (ArrayList<Device> deviceList : deviceLists) {
            for (Device device : deviceList) {
                if (device.getName().equals(deviceName)) {
                    // Update quantity, ensuring it doesn't go below 0
                    int newQuantity = device.getQuantity() + (addStock ? quantity : -quantity);
                    device.setQuantity(Math.max(newQuantity, 0));
                    return; // Exit after updating
                }
            }
        }
        System.out.println("Device not found."); // Device not found message
    }
    
    /**
     * Exports the inventory to a specified file. Each device's details, including category, name, price, and quantity,
     * are written to the file. Upon successful export, a confirmation message is displayed.
     * 
     * @param filename the name of the file to export the inventory to
     */

    public void exportInventoryToFile(String filename) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            // Write each device's details to file
            for (ArrayList<Device> deviceList : deviceLists) {
                for (Device device : deviceList) {
                    out.println("Category: " + device.getCategory() + ", Name: " + device.getName() +
                            ", Price: " + device.getPrice() + "$, Quantity: " + device.getQuantity());
                }
            }
            // Confirmation message
            System.out.println("Inventory exported to " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while exporting inventory.");
            e.printStackTrace(); // Print error stack trace
        }
    }   

    /**
     * Exports the current inventory to a file, writing details of each device across all categories.
     * Each device's category, name, price, and quantity are recorded.
     * 
     * @param filename the name of the file to which the inventory will be exported. Includes path if necessary.
     */
    
    public void restockaDevice() { //The time complexity = O(n*m)
        String deviceToRestock = "";
        boolean deviceFound = false;
    
        while (!deviceFound) {
            System.out.print("Enter the name of the device to restock (or press 'q' to go back): ");
            deviceToRestock = scanner.nextLine().trim();
    
            if (deviceToRestock.equalsIgnoreCase("q")) {
                break; // Exit to main menu
            }
            deviceFound = deviceExists(deviceToRestock);
    
            if (deviceFound) {
                String action = "";
                while (!action.equalsIgnoreCase("Add") && !action.equalsIgnoreCase("Remove")) {
                    System.out.print("Do you want to add or remove stock? (Add/Remove or press 'q' to go back): ");
                    action = scanner.nextLine().trim();
    
                    if (action.equalsIgnoreCase("q")) {
                        break; // Exit to main menu
                    } else if (!action.equalsIgnoreCase("Add") && !action.equalsIgnoreCase("Remove")) {
                        System.out.println("Invalid action. Please type 'Add' or 'Remove', or 'q' to go back.");
                    }
                }
    
                if (action.equalsIgnoreCase("q")) {
                    break; // Exit to main menu
                }
    
                int quantityChange = 0;
                System.out.print(action.equalsIgnoreCase("Add") ? "Enter the quantity to add: " : "Enter the quantity to remove: ");
                while (!scanner.hasNextInt()) {
                    scanner.nextLine(); // Clear the invalid input
                    System.out.println("Invalid input. Please enter a valid number, or 'q' to go back.");
                    System.out.print(action.equalsIgnoreCase("Add") ? "Enter the quantity to add: " : "Enter the quantity to remove: ");
                }
                quantityChange = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
    
                if (quantityChange < 0) {
                    System.out.println("Quantity cannot be negative. Please enter a positive number.");
                    continue; // Ask for the action again
                }
    
                if (action.equalsIgnoreCase("Add")) {
                    restockDevice(deviceToRestock, quantityChange, true);
                    System.out.println(deviceToRestock + " restocked. New quantity: " + getQuantityOfDevice(deviceToRestock));
                } else { // This is the "Remove" action
                    restockDevice(deviceToRestock, quantityChange, false);
                    System.out.println(deviceToRestock + " stock reduced. New quantity: " + getQuantityOfDevice(deviceToRestock));
                }
                break; // Break after successful operation
            } else {
                System.out.println("Device not found. Please try again or press 'q' to go back.");
            }
        }
    }

    /**
     * Retrieves the quantity of a specified device by name from the inventory.
     * 
     * Time complexity: O(n), where n is the total number of devices across all categories,
     * assuming a uniform distribution of devices across categories.
     * 
     * @param deviceName the name of the device whose quantity is to be retrieved.
     * @return the quantity of the specified device if found, or -1 if the device is not found.
     */
    
    public int getQuantityOfDevice(String deviceName) { //The time complexity = O(n)
        // Returns the quantity of a specified device, if found
        for (ArrayList<Device> deviceList : deviceLists) {
            for (Device device : deviceList) {
                if (device.getName().equalsIgnoreCase(deviceName)) {
                    return device.getQuantity(); // Return device quantity
                }
            }
        }
        return -1; // Device not found
    }

    /**
     * Prints an inventory report to the console, listing each device with its details.
     * Includes a summary with the total number of devices and the total inventory value.
     * 
     * Time complexity: O(n), where n is the total number of devices across all categories.
     */
    
    
    public void printInventoryReport() { //The time complexity = O(n)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'of' MMMM yyyy");
        String formattedDate = LocalDate.now().format(formatter);
    
        System.out.println("Electronics Shop Inventory Report");
        System.out.println("Generated on: " + formattedDate);
        System.out.println("----------------------------------------------------------------");
        System.out.printf("| %-4s | %-10s | %-15s | %-10s | %-9s |%n", "No.", "Category", "Name", "Price", "Quantity");
        System.out.println("----------------------------------------------------------------");
    
        int no = 1;
        double totalValue = 0.0; // For calculating total inventory value
        int totalCount = 0; // For counting total number of devices
        for (ArrayList<Device> deviceList : deviceLists) {
            for (Device device : deviceList) {
                System.out.printf("| %-4d | %-10s | %-15s | $%-9.2f | %-9d |%n", 
                                  no++, device.getCategory(), device.getName(), device.getPrice(), device.getQuantity());
                totalValue += device.getPrice() * device.getQuantity(); // Update total value
                totalCount++; // Increment total device count
            }
        }
        System.out.println("----------------------------------------------------------------\n");
        System.out.println("Summary:");
        System.out.printf("- Total Number of Devices: %d%n", totalCount);
        System.out.printf("- Total Inventory Value: $%.2f%n", totalValue);
        System.out.println("End of Report");
    }

    /**
     * Generates and optionally exports an inventory report to a specified file.
     * If a filename is provided, the report is written to the file; otherwise, it's printed to the console.
     * 
     * @param filename the name of the file to export the report to, or null/empty to print to console.
     */

    public void printInventoryReport(String filename) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'of' MMMM yyyy");
        String formattedDate = LocalDate.now().format(formatter);
        
        StringWriter reportWriter = new StringWriter();
        PrintWriter out = new PrintWriter(reportWriter);
        
        out.println("Electronics Shop Inventory Report");
        out.println("Generated on: " + formattedDate);
        out.println("----------------------------------------------------------------");
        out.printf("| %-4s | %-10s | %-15s | %-10s | %-9s |%n", "No.", "Category", "Name", "Price", "Quantity");
        out.println("----------------------------------------------------------------");
        
        int no = 1;
        double totalValue = 0.0; // For calculating total inventory value
        int totalCount = 0; // For counting total number of devices
        for (ArrayList<Device> deviceList : deviceLists) {
            for (Device device : deviceList) {
                out.printf("| %-4d | %-10s | %-15s | $%-9.2f | %-9d |%n", 
                          no++, device.getCategory(), device.getName(), device.getPrice(), device.getQuantity());
                totalValue += device.getPrice() * device.getQuantity(); // Update total value
                totalCount++; // Increment total device count
            }
        }
        out.println("----------------------------------------------------------------\n");
        out.println("Summary:");
        out.printf("- Total Number of Devices: %d%n", totalCount);
        out.printf("- Total Inventory Value: $%.2f%n", totalValue);
        out.println("End of Report");
    
        // If filename is provided, write the report to a file
        if (filename != null && !filename.isEmpty()) {
            try (PrintWriter fileOut = new PrintWriter(filename)) {
                fileOut.println(reportWriter.toString());
                System.out.println("Report exported to " + filename);
            } catch (IOException e) {
                System.out.println("An error occurred while exporting the report.");
                e.printStackTrace();
            }
        } else {
            // Otherwise, print the report to the console
            System.out.println(reportWriter.toString());
        }
    }

    /**
     * Checks if a device exists in the inventory by its name.
     * 
     * @param deviceName the name of the device to check.
     * @return true if the device exists, false otherwise.
     */
    
    public boolean deviceExists(String deviceName) {
        for (ArrayList<Device> deviceList : deviceLists) {
            for (Device device : deviceList) {
                if (device.getName().equalsIgnoreCase(deviceName)) {
                    return true; // Device found
                }
            }
        }
        return false; // Device not found
    }
           
}