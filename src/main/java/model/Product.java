package model;

public class Product extends InventoryItem {
    private int id;
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price) {
        this(0, name, quantity, price);
    }

    public Product(int id, String name, int quantity, double price) {
        super(name);
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String getDetails() {
        return String.format("ID:%d | %s | Qty:%d | Price:$%.2f",
                id, getName(), quantity, price);
    }
}