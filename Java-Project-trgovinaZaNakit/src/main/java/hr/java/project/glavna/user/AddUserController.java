package hr.java.project.glavna.user;


import hr.java.project.data.UserData;
import hr.java.project.glavna.fxutil.Notification;
import hr.java.project.glavna.fxutil.UserValidate;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddUserController {

    @FXML
    private TextField idField, passwordField, nameField, surnameField;

    @FXML
    private ComboBox<String> roleField;
    @FXML
    public void initialize(){
        clearFields();
        roleField.setItems(FXCollections.observableArrayList("ADMIN", "KUPAC"));
    }

    public void add(){
        if(roleField.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error while creating new users.");
            alert.setContentText("Please select users's role!");
            alert.show();
        }else{
            if(UserValidate.isNameValid(nameField.getText()) && UserValidate.isNameValid(surnameField.getText()) && Notification.confirmEdit()){
                UserData.createNewUser(idField.getText(), nameField.getText(), surnameField.getText(), roleField.getSelectionModel().getSelectedItem(),passwordField.getText());
                initialize();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error while creating new users.");
                alert.setContentText("Name and surname need to contain only alphabetic characters.\nOib needs to contain 10 numeric characters!");
                alert.show();
            }
        }
    }

    public void clearFields(){
        idField.setText("");
        passwordField.setText("");
        nameField.setText("");
        surnameField.setText("");
        roleField.getSelectionModel().select("ADMIN");
    }

}
