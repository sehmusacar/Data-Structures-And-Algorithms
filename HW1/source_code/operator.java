public class operator extends person {
    private int wage; // Operator's wage
    private customer[] customers; // Array to store customers assigned to the operator
    private int numCustomers; // Number of customers assigned to the operator

    // Constructor
    public operator(String name, String surname, String address, String phoneNumber, int ID, int wage, int maxCustomers) {
        // Call superclass constructor
        super(name, surname, address, phoneNumber, ID);
        // Initialize operator's wage and customer array
        this.wage = wage;
        this.customers = new customer[maxCustomers];
        this.numCustomers = 0;
    }

    // Getter and setter methods
    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    // Method to add a customer to the operator's list
    public void addCustomer(customer customer) {
        if (numCustomers < customers.length) {
            customers[numCustomers] = customer;
            numCustomers++;
        } else {
            System.out.println("Cannot add more customers. Maximum capacity reached.");
        }
    }

    // Method to return the list of customers associated with this operator
    public customer[] getCustomers() {
        return customers;
    }
}
