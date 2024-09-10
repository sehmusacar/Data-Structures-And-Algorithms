public class corporate_customer extends customer {
    // Additional attribute specific to corporate customers
    private String companyName;

    // Constructor
    public corporate_customer(String name, String surname, String address, String phoneNumber, int ID, int customerNumber, String companyName, int operatorID) {
        // Call superclass constructor to initialize common attributes
        super(name, surname, address, phoneNumber, ID, customerNumber, operatorID);
        // Initialize additional attribute
        this.companyName = companyName;
    }

    // Getter method for companyName
    public String getCompanyName() {
        return companyName;
    }

    // Setter method for companyName
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
