package model;

public abstract class InventoryItem {
    private String name;

    public InventoryItem(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public abstract String getDetails();

    @Override
    public String toString() {
        return getDetails();
    }
}
