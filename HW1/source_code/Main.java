import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // List to store orders
        order[] orderList = new order[100]; // Assuming a maximum of 100 orders
        int orderCount = 0; // Counter to keep track of the number of orders
        // HashMap to map operator ID to their customers
        HashMap<Integer, customer[]> operatorcustomers = new HashMap<>();
        // List to store all persons (operators and customers)
        person[] personList = new person[100]; // Assuming a maximum of 100 persons
        int personCount = 0; // Counter to keep track of the number of persons

        try {
            // Read data from file
            File myFile = new File("content.txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                // Split each line of data by semicolon
                String[] data = myReader.nextLine().split(";");

                // Process data based on type
                switch(data[0]) {
                    case "order":
                        // If it's an order, create a new order object and add it to the order list
                        orderList[orderCount] = new order(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), data[4], Integer.parseInt(data[5]));
                        orderCount++;
                        break;
                    case "retail_customer":
                    case "corporate_customer":
                        // If it's a customer, create either retail_customer or corporate_customer object based on the type and add it to the person list
                        customer cust = data[0].equals("retail_customer") 
                                        ? new retail_customer(data[1], data[2], data[3], data[4], Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[6]))
                                        : new corporate_customer(data[1], data[2], data[3], data[4], Integer.parseInt(data[5]), Integer.parseInt(data[6]), data[7], Integer.parseInt(data[6]));
                        personList[personCount] = cust;
                        personCount++;
                        // Map the customer to the operator using operator ID
                        int operatorID = Integer.parseInt(data[6]);
                        if (!operatorcustomers.containsKey(operatorID)) {
                            operatorcustomers.put(operatorID, new customer[100]); // Assuming a maximum of 100 customers per operator
                        }
                        customer[] customers = operatorcustomers.get(operatorID);
                        int customerIndex = 0;
                        while (customers[customerIndex] != null) {
                            customerIndex++;
                        }
                        customers[customerIndex] = cust;
                        break;
                        case "operator":
                        // If it's an operator, create a new operator object and add it to the person list
                        personList[personCount] = new operator(data[1], data[2], data[3], data[4], Integer.parseInt(data[5]), Integer.parseInt(data[6]), 100);
                        personCount++;
                        break;
                }
            }
            myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Scanner input = new Scanner(System.in)) {
            // Prompt the user to enter ID
            System.out.println("Please enter your ID...");
            int id = input.nextInt();
            person matchedperson = null; // To store the matched person

            // Find the person with the entered ID
            for(int i = 0; i < personCount; i++) {
                if(personList[i].getID() == id) {
                    matchedperson = personList[i]; // Store the match
                    break; // Break the loop since match found
                }
            }

            // Display information based on the type of the matched person
            if (matchedperson != null) {
                if(matchedperson instanceof operator) {
                    // If the matched person is an operator
                    operator op = (operator)matchedperson;
                    System.out.println("*** operator Screen ***");
                    System.out.println("------------------------");
                    System.out.println("Name & Surname: " + op.getName() + " " + op.getSurname());
                    System.out.println("Address: " + op.getAddress());
                    System.out.println("Phone: " + op.getPhoneNumber());
                    System.out.println("ID: " + op.getID());
                    System.out.println("Wage: " + op.getWage());
                    System.out.println("------------------------");
            
                    // Display customers of the operator
                    customer[] customers = operatorcustomers.getOrDefault(op.getID(), new customer[0]);
                    if(customers.length == 0) {
                        System.out.println("This operator doesn't have any customer.");
                    } else {
                        int customerNumber = 1;
                        for(customer c : customers) {
                            if (c == null) continue; // Skip null entries in the array
                            System.out.println("customer #" + customerNumber + " (" + (c instanceof retail_customer ? "a retail customer" : "a corporate customer") + "):");
                            System.out.println("Name & Surname: " + c.getName() + " " + c.getSurname());
                            System.out.println("Address: " + c.getAddress());
                            System.out.println("Phone: " + c.getPhoneNumber());
                            System.out.println("ID: " + c.getID());
                            System.out.println("operator ID: " + op.getID());
                            if(c instanceof corporate_customer) {
                                System.out.println("Company name: " + ((corporate_customer)c).getCompanyName());
                            }
            
                            int orderNumber = 1;
                            for (int j = 0; j < orderCount; j++) {
                                order o = orderList[j];
                                if (o.getOrderID() == c.getID()) {
                                    int statusCode = Integer.parseInt(o.getStatus());
                                    o.setStatus(statusCode);
                                    
                                    if (o.getCount() > 0) {
                                        System.out.println("order #" + orderNumber + " => Product name: " + o.getProductName() + " - Count: " + o.getCount() + " - Total price: " + o.getTotalPrice() + " - Status: " + o.getStatus());
                                        orderNumber++;
                                    }
                                }
                            }
                            System.out.println("------------------------");
                            customerNumber++;
                        }
                    }
                } else if(matchedperson instanceof customer) {
                    // If the matched person is a customer
                    customer c = (customer)matchedperson;
                    System.out.println("*** customer Screen ***");
                    System.out.println("Name & Surname: " + c.getName() + " " + c.getSurname());
                    System.out.println("Address: " + c.getAddress());
                    System.out.println("Phone: " + c.getPhoneNumber());
                    System.out.println("ID: " + c.getID());
                    System.out.println("operator ID: " + c.getID());
                    if(c instanceof corporate_customer) {
                        System.out.println("Company name: " + ((corporate_customer)c).getCompanyName());
                    }
                
                    // Display orders of the customer
                    int orderNumber = 1;
                    for(int i = 0; i < orderCount; i++) {
                        order o = orderList[i];
                        if(o.getOrderID() == c.getID()) {
                            // Update order status
                            int statusCode = Integer.parseInt(o.getStatus());
                            o.setStatus(statusCode);
                            
                            if(o.getCount() > 0) {
                                System.out.println("order #" + orderNumber + " => Product name: " + o.getProductName() + " - Count: " + o.getCount() + " - Total price: " + o.getTotalPrice() + " - Status: " + o.getStatus());
                                orderNumber++;
                            }
                        }
                    }
                }
            } else {
                System.out.println("No operator/customer was found with ID "+ id + ".Please try again.");
            }
        } 
    }
}
