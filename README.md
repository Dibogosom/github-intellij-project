#  Inventory Management System



A clean JavaFX desktop application for managing product inventory with PostgreSQL backend.

## Features
- Add new products (name, quantity, price)
- Update stock quantity
- View all products in a TableView
- Automatic **red highlighting** when a product is out of stock (quantity = 0)
- Input validation and error handling

## Technologies Used
- **JavaFX** – GUI
- **PostgreSQL** – Database
- **JDBC** – Database connectivity
- **Maven** – Build tool

## OOP Principles Demonstrated
- **Encapsulation**: Private fields + getters/setters in `Product` class
- **Inheritance**: `Product` extends abstract `InventoryItem` class
- **Polymorphism**: Overriding `getDetails()` method
- **Generics**: Generic method `getAllItems(Class<T> type)` in DAO

## How to Run the Project

1. Clone the repository
2. Create PostgreSQL database `inventorydb`
3. Run the table creation SQL (see `README` or project files)
4. Update your PostgreSQL password in `src/main/java/util/DatabaseConnection.java`
5. Run `MainApp.java` using Maven (`javafx:run`) or directly in IntelliJ

