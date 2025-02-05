package hr.java.project.glavna.products;


import hr.java.project.data.RobaData;
import hr.java.project.entiteti.Product;
import hr.java.project.glavna.fxutil.HelperMethods;
import hr.java.project.glavna.fxutil.Notification;
import hr.java.project.iznimke.ObjectExistsException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public final class EditProductsScreenController implements HelperMethods, Notification, RobaData {

    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, String> priceColumn,idColumn, nameColumn, categoryColumn, sizeColumn,colorColumn, genderColumn, descriptionColumn;
    @FXML
    private TextField nameField, categoryField, priceField;
    @FXML
    private ComboBox<String> genderField;
    @FXML
    ComboBox<String> colorPicker;

    @FXML
    private CheckBox xs,s, m, l,xl, none;
    @FXML
    private Label idLabel, nameLabel, categoryLabel, sizeLabel;
    @FXML
    private TextArea descriptionArea;

    @FXML
    private ObservableList<Product> productsObservableList;


    @FXML
    public void initialize(){
        List<Product> products=RobaData.getAllRoba();

        fillTable(products);

        genderField.setItems(FXCollections.observableArrayList("M", "F","NONE"));
        colorPicker.setItems(FXCollections.observableArrayList(
                "RED", "BLUE", "GREEN", "PINK", "ORANGE", "PURPLE", "WHITE", "BROWN", "TURQUOISE", "YELLOW","BLACK" ,"NONE"
        ));

        xs.setOnAction(event -> handleCheckboxSelection(xs));
        s.setOnAction(event -> handleCheckboxSelection(s));
        m.setOnAction(event -> handleCheckboxSelection(m));
        l.setOnAction(event -> handleCheckboxSelection(l));
        xl.setOnAction(event -> handleCheckboxSelection(xl));
        none.setOnAction(event -> handleCheckboxSelection(none));

        // Initialize the checkboxes
        s.setSelected(false);
        m.setSelected(false);
        l.setSelected(false);
        none.setSelected(true);
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

    public void productUpdate() throws ObjectExistsException {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Get the updated values from the fields and checkboxes
            String name = nameField.getText();
            String category = categoryField.getText();
            String color = colorPicker.getValue();
            String size = "";
            if (s.isSelected()) {
                size += "S ";
            }
            if (m.isSelected()) {
                size += "M ";
            }
            if (l.isSelected()) {
                size += "L ";
            }
            if (none.isSelected()) {
                size += "NONE";
            }
            if (xs.isSelected()) {
                size += "XS ";
            }
            if (xl.isSelected()) {
                size += "XL ";
            }
            String gender = genderField.getValue();
            String description = descriptionArea.getText();
            Double price = Double.valueOf(priceField.getText());
            RobaData.updateRoba(name, category, price, size, color, description, gender, selectedProduct.getId());
            // Update the selected product with the new values
            selectedProduct.setNaziv(name);
            selectedProduct.setKategorija(category);
            selectedProduct.setBoja(color);
            selectedProduct.setVelicina(size);
            selectedProduct.setSpol(gender);
            selectedProduct.setPrice(price);
            selectedProduct.setDescription(description);



            productsTableView.refresh(); // Refresh the table view
            clearFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error while updating product.");
            alert.show();
        }
    }




    public void clearFields(){

    }

    public void onSelect() {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            nameField.setText(selectedProduct.getNaziv());
            categoryField.setText(selectedProduct.getKategorija());
            priceField.setText(String.valueOf(selectedProduct.getPrice()));
            genderField.setValue(selectedProduct.getSpol());
            colorPicker.setValue(selectedProduct.getBoja());

            descriptionArea.setText(String.valueOf(selectedProduct.getDescription()));

            // Set the appropriate checkbox based on the product size
            String size = selectedProduct.getVelicina();
            s.setSelected(size.equals("S"));
            m.setSelected(size.equals("M"));
            l.setSelected(size.equals("L"));
            xl.setSelected(size.equals("L"));
            xs.setSelected(size.equals("S"));
            none.setSelected(size.isEmpty());
        }
    }


    public void delete() throws SQLException, IOException {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null && Notification.confirmEdit()) {
            RobaData.deleteProduct(selectedProduct.getId());
            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error while deleting user.");
            alert.setContentText("Please select a user first!");
            alert.show();
        }
    }
    private void handleCheckboxSelection(CheckBox selectedCheckbox) {
        // Deselect all checkboxes except the selected one
        if (selectedCheckbox.isSelected()) {
            s.setSelected(selectedCheckbox == s);
            m.setSelected(selectedCheckbox == m);
            l.setSelected(selectedCheckbox == l);
            none.setSelected(selectedCheckbox == none);
        } else {
            // If the selected checkbox is deselected, select the "none" checkbox
            none.setSelected(true);
        }
    }



}
