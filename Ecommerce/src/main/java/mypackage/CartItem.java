package mypackage;

public class CartItem {
    private int productId;
    private String name;
    private double price;
    private int quantity;
    private String size;

    public CartItem(int productId, String name, double price, int quantity, String size) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getTotalPrice() {
        return this.price * this.quantity;
    }
}
