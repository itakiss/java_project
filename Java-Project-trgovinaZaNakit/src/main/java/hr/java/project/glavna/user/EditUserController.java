package hr.java.project.glavna.user;


import hr.java.project.data.UserData;
import hr.java.project.entiteti.User;
import hr.java.project.glavna.fxutil.Notification;
import hr.java.project.glavna.fxutil.UserValidate;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.xml.validation.Validator;
import java.util.Optional;

import java.util.Optional;

public class EditUserController {

    @FXML
    private TextField  nameField, surnameField;

    @FXML
    private Label idLabel;

    @FXML
    private ComboBox<String> roleField;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> idColumn, nameColumn, surnameColumn, roleColumn;

    private ObservableList<User> userObservableList;

    public void initialize() {
        loadUserData();

        // Other initialization code
        roleField.setItems(FXCollections.observableArrayList("ADMIN", "KUPAC"));

        clearFields();
    }

    public void update() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String newName = nameField.getText();
            String newSurname = surnameField.getText();
            String newRole = roleField.getSelectionModel().getSelectedItem();

            UserData.updateUser(selectedUser.getId(), selectedUser.getPassword(), newName, newSurname, newRole);
            loadUserData();
            clearFields();

        }

             else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error while updating user.");
                alert.show();
            }
        }


    // ...

    private void loadUserData() {
        userObservableList = FXCollections.observableList(UserData.allUserDatabase());

        idColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getId()));
        nameColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getName()));
        surnameColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getSurname()));
        roleColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getRole()));

        userTableView.setItems(userObservableList);
        userTableView.refresh();
    }
    public void onSelect() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            nameField.setText(selectedUser.getName());
            surnameField.setText(selectedUser.getSurname());
            roleField.getSelectionModel().select(selectedUser.getRole());
        }
    }
    public void delete() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null && !selectedUser.getRole().equals("ADMIN") && Notification.confirmEdit()) {
            UserData.deleteUser(selectedUser.getId());
            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error while deleting user.");
            alert.setContentText("Please select a user first!");
            alert.show();
        }
    }

    public void clearFields() {
        idLabel.setText("");
        nameField.setText("");
        surnameField.setText("");
        roleField.getSelectionModel().select("ADMIN");
    }
}

