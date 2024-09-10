/**
 * The time complexity of each method of this class is O(1) 
 * because each operation takes constant time and the processing 
 * time does not vary depending on the input size.
 */

 public class Tablet implements Device {
    private String category;
    private String name;
    private double price;
    private int quantity;

    public Tablet(String name, double price, int quantity) {
        this.category = "Tablet";
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
