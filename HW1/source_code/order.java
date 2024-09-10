public class order {
    private String productName;
    private int count;
    private int totalPrice;
    private String status;
    private int orderID;
    private int customerID;
    private static final String[] statusArray = {"Initialized", "Processing", "Completed"};

    // Constructor
    public order(String productName, int count, int totalPrice, String status, int orderID) {
        this.productName = productName;
        this.count = count;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderID = orderID;
    }

    // Getter methods
    public String getProductName() {
        return productName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getCount() {
        return count;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public int getOrderID() {
        return orderID;
    }

    // Setter methods
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    // Method to set status based on status code
    public void setStatus(int statusCode) {
        if (statusCode >= 0 && statusCode < statusArray.length) {
            this.status = statusArray[statusCode];
        } else {
            this.status = "Canceled";
        }
    }
}
