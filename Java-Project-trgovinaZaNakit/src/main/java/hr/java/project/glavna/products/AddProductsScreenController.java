package hr.java.project.glavna.products;

import hr.java.project.data.RobaData;
import hr.java.project.data.UserData;
import hr.java.project.entiteti.Product;
import hr.java.project.glavna.fxutil.Notification;
import hr.java.project.glavna.fxutil.UserValidate;
import hr.java.project.iznimke.ObjectExistsException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class AddProductsScreenController {


    @FXML
    private TextField nameField, categoryField, priceField, quantityField;
    @FXML
    TextArea descriptionFieldd;
    @FXML
    private ComboBox<String> genderField, colorField, sizeField;


    @FXML
    public void initialize(){
        clearFields();
        genderField.setItems(FXCollections.observableArrayList("M", "F","NONE"));
        colorField.setItems(FXCollections.observableArrayList("RED", "BLUE", "GREEN", "PINK", "ORANGE", "PURPLE", "WHITE", "BROWN", "TURQUOISE", "YELLOW","BLACK" ,"NONE"));
        sizeField.setItems(FXCollections.observableArrayList("XS","S","M","L","XL", "NONE"));
    }

    public void add() throws ObjectExistsException, SQLException, IOException {
        if (genderField.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error while creating new product.");
            alert.setContentText("Please select product gender!");
            alert.show();
        } else {
            if (Notification.confirmEdit()) {
                String name = nameField.getText();
                String category = categoryField.getText();
                String size = sizeField.getValue();
                String color = colorField.getValue();
                String quantity = quantityField.getText();
                String gender = genderField.getValue();
                String description = descriptionFieldd.getText();
                Double price = Double.valueOf(priceField.getText());
                // Update the selected product with the new values

                RobaData.addRoba(name, category, price, size, color, description, gender);
                initialize();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error while creating new users.");
                alert.setContentText("Name and surname need to contain only alphabetic characters.\nOib needs to contain 10 numeric characters!");
                alert.show();
            }
        }
    }

    public void clearFields(){
        nameField.setText("");
        categoryField.setText("");
        sizeField.getSelectionModel().select("NONE");
        colorField.getSelectionModel().select("NONE");
        quantityField.setText("");
        genderField.getSelectionModel().select("NONE");
        descriptionFieldd.setText("");
        priceField.setText("");
    }

}