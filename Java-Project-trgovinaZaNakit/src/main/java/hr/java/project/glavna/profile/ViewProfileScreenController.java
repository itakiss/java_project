package hr.java.project.glavna.profile;

import hr.java.project.ProjectApplication;
import hr.java.project.data.UserData;
import hr.java.project.entiteti.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static hr.java.project.data.UserData.getEmailFromUserId;

public class ViewProfileScreenController implements Initializable {
    @FXML
    private TextField emailField;
    @FXML
    private Button updateButton;

    private User currentUser; // Assuming you have a User class

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get the current user from the database or any other source
        if(getEmailFromUserId(ProjectApplication.getLoggedUser().getId())!=null){
            emailField.setText(getEmailFromUserId(ProjectApplication.getLoggedUser().getId()));
        };

    }

    @FXML
    public void updateEmail() {

        UserData.updateCustomer(ProjectApplication.getLoggedUser().getId(), emailField.getText());

        // Show a confirmation message to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Email Update");
        alert.setHeaderText("Email Updated Successfully");
        alert.setContentText("Your email has been updated to: " + emailField.getText());
        alert.showAndWait();
    }


}
