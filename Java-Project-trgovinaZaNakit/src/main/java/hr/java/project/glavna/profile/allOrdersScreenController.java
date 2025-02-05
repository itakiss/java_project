package hr.java.project.glavna.profile;

import hr.java.project.entiteti.Product;
import hr.java.project.glavna.fxutil.Order;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class allOrdersScreenController {

    @FXML
    private TableView<Product> ordersTableView;
    @FXML
    private TableColumn<Product, String> idColumn, product_idColumn, product_nameColumn, user_idColumn, shipping_addressColumn, order_emailColumn, order_dateColumn, order_priceColumn;

    @FXML
    private TextField filterField, product_nameField, user_idField;
    @FXML
    ObservableList<Order> orderObservableList;



}
