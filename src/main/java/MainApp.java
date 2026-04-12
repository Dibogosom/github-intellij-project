import dao.ProductDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Product;

public class MainApp extends Application {

    private ProductDAO dao = new ProductDAO();
    private TableView<Product> table = new TableView<>();
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Inventory Management System ");

        // Add Product Form
        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");
        TextField qtyField = new TextField();
        qtyField.setPromptText("Quantity");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        Button addBtn = new Button("Add Product");
        addBtn.setOnAction(e -> addProduct(nameField, qtyField, priceField));

        HBox addForm = new HBox(10, nameField, qtyField, priceField, addBtn);
        addForm.setPadding(new Insets(10));

        // Update Stock Form
        TextField idField = new TextField();
        idField.setPromptText("Product ID");
        TextField newQtyField = new TextField();
        newQtyField.setPromptText("New Quantity");
        Button updateBtn = new Button("Update Stock");
        updateBtn.setOnAction(e -> updateStock(idField, newQtyField));

        HBox updateForm = new HBox(10, idField, newQtyField, updateBtn);
        updateForm.setPadding(new Insets(10));

        // TableView
        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTextFill(Color.BLACK);
                } else {
                    setText(item);
                    Product p = getTableRow().getItem();
                    if (p != null && p.getQuantity() == 0) {
                        setTextFill(Color.RED);
                    } else {
                        setTextFill(Color.BLACK);
                    }
                }
            }
        });

        TableColumn<Product, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price(Ksh)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(idCol, nameCol, qtyCol, priceCol);
        table.setItems(productList);

        Button refreshBtn = new Button("Refresh Table");
        refreshBtn.setOnAction(e -> loadProducts());

        VBox root = new VBox(15,
                new Label("Add New Product"), addForm,
                new Label("Update Stock"), updateForm,
                new Label("All Products (Name turns RED when out of stock)"),
                table, refreshBtn);

        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 950, 650);
        stage.setScene(scene);
        loadProducts();
        stage.show();
    }
    private void addProduct(TextField name, TextField qty, TextField price) {
        try {
            String n = name.getText().trim();
            if (n.isEmpty()) throw new Exception("Product name is required");
            int q = Integer.parseInt(qty.getText());
            double p = Double.parseDouble(price.getText());
            if (q < 0 || p < 0) throw new Exception("Quantity and Price must be >= 0");

            Product product = new Product(n, q, p);
            dao.addProduct(product);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Product added!");
            loadProducts();           // already calls refresh now
            name.clear(); qty.clear(); price.clear();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Input Error", ex.getMessage());
        }
    }

    private void updateStock(TextField idField, TextField qtyField) {
        try {
            int id = Integer.parseInt(idField.getText());
            int qty = Integer.parseInt(qtyField.getText());
            dao.updateQuantity(id, qty);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Stock updated!");
            loadProducts();           // already calls refresh now
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid ID or quantity");
        }
    }




    private void loadProducts() {
        try {
            productList.clear();
            productList.addAll(dao.getAllItems(Product.class));

            // ← THIS FIXES the "last item not turning red" bug
            table.refresh();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
