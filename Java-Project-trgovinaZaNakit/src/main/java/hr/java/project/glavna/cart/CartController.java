package hr.java.project.glavna.cart;

import hr.java.project.ProjectApplication;
import hr.java.project.data.CartData;
import hr.java.project.entiteti.*;
import hr.java.project.glavna.api.EmailUtil;
import hr.java.project.glavna.fxutil.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CartController {
    @FXML
    private TableView<Product> cartTableView;
    @FXML
    private TableColumn<Product, String> idColumn, nameColumn, categoryColumn, sizeColumn, colorColumn, genderColumn, descriptionColumn, priceColumn;
    @FXML
    private TextField filterField;
    private Order order;
    private Cart cart;
    private ObservableList<Product> cartObservableList;

    public void initialize() {
        cart = new Cart();
        List<Product> items = CartData.getCartsByUserId(ProjectApplication.getLoggedUser().getId());
        fillTable(items);
    }

    public void fillTable(List<Product> items) {
        cartObservableList = FXCollections.observableArrayList(items);

        idColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getId().toString()));
        nameColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getNaziv()));
        categoryColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getKategorija()));
        colorColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getBoja()));
        sizeColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getVelicina()));
        genderColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getSpol()));
        descriptionColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getDescription()));
        priceColumn.setCellValueFactory(product -> new SimpleStringProperty(String.valueOf(product.getValue().getPrice())));

        cartTableView.setItems(cartObservableList);
    }

    @FXML
    private void removeFromCart() throws SQLException, IOException {
        Product selectedProduct = cartTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (CartData.deleteProduct(selectedProduct)) {
                cartObservableList.remove(selectedProduct); // Remove the selected item from the observable list
                cartTableView.refresh(); // Refresh the table view
                Alert alert = new Alert(Alert.AlertType.NONE, "Removed Successfully!", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE, "Removal Failed!", ButtonType.OK);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "Please select a product for removal!", ButtonType.OK);
            alert.showAndWait();
        }
    }


    @FXML
    private void buyFromCart() {

        List<Product> cartItems = cartTableView.getItems();
        if (cartItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "The cart is empty!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Calculate the total price
        double totalPrice = cartItems.stream().mapToDouble(Product::getPrice).sum();

        // Get buyer information
        User user = ProjectApplication.getLoggedUser();
        String orderId = generateOrderId(); // Generate a unique order ID
        LocalDate purchaseDate = LocalDate.now();

        // Create the order
        order = new Order(orderId, user, cartItems, purchaseDate, totalPrice);

        // Write the purchase details to a file
        String fileName = "dat/purchase.txt";
        order.writeToTextFile(fileName);

        // Send the email confirmation
        order.sendConfirmationEmail(fileName);

        // Delete the cart
        boolean cartDeleted = CartData.deleteCart(user.getId());
        if (cartDeleted) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Purchase completed. Cart deleted.", ButtonType.OK);
            alert.showAndWait();
            // Clear the table```java
            cartObservableList.clear();
            cartTableView.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "Failed to delete the cart!", ButtonType.OK);
            alert.showAndWait();

        }
    }
    public String generateOrderId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "ORDER-" + now.format(formatter);
    }
}