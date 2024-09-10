public class person {
    // Attributes
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private int ID;

    // Constructor
    public person(String name, String surname, String address, String phoneNumber, int ID) {
        // Initialize attributes with provided values
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.ID = ID;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getID() {
        return ID;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
