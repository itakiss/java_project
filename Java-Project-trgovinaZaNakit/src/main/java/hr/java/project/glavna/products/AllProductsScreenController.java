package hr.java.project.glavna.products;


import hr.java.project.ProjectApplication;
import hr.java.project.data.CartData;
import hr.java.project.data.DataBase;
import hr.java.project.data.RobaData;
import hr.java.project.entiteti.Cart;
import hr.java.project.entiteti.Product;
import hr.java.project.glavna.fxutil.UserValidate;
import hr.java.project.glavna.fxutil.Notification;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.stream.Collectors;

public final class AllProductsScreenController implements DataBase, UserValidate, RobaData, Notification {

    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, String> idColumn, nameColumn, categoryColumn, sizeColumn,colorColumn, genderColumn, descriptionColumn, priceColumn;

    @FXML
    private TextField filterField, nameEditField, categoryField, colorField;
    @FXML
    private ObservableList<Product> productsObservableList;



    @FXML
    public void initialize(){
        List<Product> products=RobaData.getAllRoba();

        fillTable(products);
    }

    public void fillTable(List<Product> productlist){
        productsObservableList = FXCollections.observableArrayList(productlist);

        idColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getId().toString()));
        nameColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getNaziv()));
        categoryColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getKategorija()));
        colorColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getBoja()));
        sizeColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getVelicina()));
        genderColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getSpol()));
        priceColumn.setCellValueFactory(product -> new SimpleStringProperty(String.valueOf(product.getValue().getPrice())));
        descriptionColumn.setCellValueFactory(product -> new SimpleStringProperty(String.valueOf(product.getValue().getDescription())));

        productsTableView.setItems(productsObservableList);
    }

    public void search() {
        String filter = filterField.getText().toLowerCase();

        List<Product> filteredProduct = RobaData.getAllRoba().stream().filter(roba ->
                roba.getNaziv().toLowerCase().contains(filter) ||
                        roba.getBoja().toLowerCase().contains(filter) ||
                        roba.getKategorija().toLowerCase().contains(filter) ||
                        String.valueOf(roba.getId()).contains(filter) ||
                        String.valueOf(roba.getPrice()).contains(filter) ||
                        roba.getSpol().toLowerCase().contains(filter) ||
                        roba.getVelicina().toLowerCase().contains(filter) ||
                        roba.getDescription().toLowerCase().contains(filter)
        ).collect(Collectors.toList());

        fillTable(filteredProduct);

        productsTableView.getSelectionModel().clearSelection();
    }



    @FXML
    public void addToCartButton() {
        productsTableView.getItems().removeAll(productsTableView.getSelectionModel().getSelectedItem());
        if (productsTableView.getSelectionModel().getSelectedItem() != null) {
            Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
            if (CartData.saveToCart(selectedProduct,ProjectApplication.getLoggedUser().getId())) {
                Alert alert = new Alert(Alert.AlertType.NONE, selectedProduct.getNaziv() +" Added Successfully!", ButtonType.OK);
                alert.showAndWait();
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE, "Product Adding Failed!", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.NONE, "Please Select Product To Add!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

    }








}

