package hr.java.project.glavna.changes;


import hr.java.project.data.RobaData;
import hr.java.project.entiteti.Product;
import hr.java.project.entiteti.User;
import hr.java.project.glavna.fxutil.ChangeWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChangesProductsController<T> {

    @FXML
    private TableView<Product> tableView;

    @FXML
    TableColumn<Product,String> idColumn, nameColumn, categoryColumn, sizeColumn,colorColumn, genderColumn, descriptionColumn, priceColumn;

    @FXML
    private Label changeLabel;

    private ChangeWriter reader;
    @FXML
    private ObservableList<Product> productsObservableList;

    @FXML
    public void initialize(){
        reader = new ChangeWriter();
        List<Product> allChanges = reader.readRoba();
        List<Product> newRoba = new ArrayList<>();
        for(int i = 1;i<allChanges.size();i+=2){
            newRoba.add(allChanges.get(i));
        }
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

        tableView.setItems(productsObservableList);
    }


    public void onSelect() {
        Optional<Product> selectedProduct = Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());

        if (selectedProduct.isPresent()) {
            List<String> times = reader.readTimeRoba();
            List<String> roles = reader.readRoleChangeRoba();


            changeLabel.setText("Changes made: " + times.get(tableView.getSelectionModel().getSelectedIndex()) + " " + " by " + roles.get(tableView.getSelectionModel().getSelectedIndex()));

        }
    }

    public void moreInfo(){
        Optional<Product> selectedProduct = Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());

        List<Product> allChanges = reader.readRoba();
        List<Product> oldProducts = new ArrayList<>();
        for(int i = 0;i<allChanges.size();i+=2){
            oldProducts.add(allChanges.get(i));
        }

        Alert alert;
        if(selectedProduct.isPresent()){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setHeaderText("More info about product change.");
            alert.setContentText("OLD VALUE:\n" + oldProducts.get(tableView.getSelectionModel().getSelectedIndex()) + "\n\nNEW VALUE:\n" + selectedProduct.get());
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("No product change selected!");
            alert.setContentText("Please select products to show more info!");
        }
        alert.show();
    }

}

