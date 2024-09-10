public class customer extends person {
    // Additional attributes
    private int customerNumber;
    private int operatorID;

    // Constructor
    public customer(String name, String surname, String address, String phoneNumber, int ID, int customerNumber, int operatorID) {
        // Call superclass constructor to initialize common attributes
        super(name, surname, address, phoneNumber, ID);
        // Initialize additional attributes
        this.customerNumber = customerNumber;
        this.operatorID = operatorID;
    }

    // Getter method for customerNumber
    public int getCustomerNumber() {
        return customerNumber;
    }

    // Setter method for customerNumber
    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    // Getter method for operatorID
    public int getOperatorID() {
        return operatorID;
    }

    // Setter method for operatorID
    public void setOperatorID(int operatorID) {
        this.operatorID = operatorID;
    }
}
