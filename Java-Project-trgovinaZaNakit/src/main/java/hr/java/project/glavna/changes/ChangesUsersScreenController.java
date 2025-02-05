package hr.java.project.glavna.changes;


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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChangesUsersScreenController {

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User,String> idColumn, passwordColumn, nameColumn, surnameColumn, roleColumn;

    @FXML
    private Label changeLabel;

    private ChangeWriter reader;

    @FXML
    public void initialize(){
        reader = new ChangeWriter();
        List<User> allChanges = reader.readKorisnik();
        List<User> newKorisnik = new ArrayList<>();
        for(int i = 1;i<allChanges.size();i+=2){
            newKorisnik.add(allChanges.get(i));
        }

        ObservableList<User> userObservableList = FXCollections.observableList(newKorisnik);

        idColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getId()));
        passwordColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getPassword()));
        nameColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getName()));
        surnameColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getSurname()));
        roleColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getRole()));

        tableView.setItems(userObservableList);
    }

    public void onSelect(){
        Optional<User> selectedUser = Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());

        if(selectedUser.isPresent()){
            List<String> times = reader.readTimeKorisnik();
            List<String> roles = reader.readRoleChangeKorisnik();

            changeLabel.setText("Changes made: " + times.get(tableView.getSelectionModel().getSelectedIndex()) + " " + " by " + roles.get(tableView.getSelectionModel().getSelectedIndex()));

        }
    }

    public void moreInfo(){
        Optional<User> selectedKorisnik = Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());

        List<User> allChanges = reader.readKorisnik();
        List<User> oldUsers = new ArrayList<>();
        for(int i = 0;i<allChanges.size();i+=2){
            oldUsers.add(allChanges.get(i));
        }

        Alert alert;
        if(selectedKorisnik.isPresent()){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setHeaderText("More info about users change.");
            alert.setContentText("OLD VALUE:\n" + oldUsers.get(tableView.getSelectionModel().getSelectedIndex()) + "\n\nNEW VALUE:\n" + selectedKorisnik.get());
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("No users change selected!");
            alert.setContentText("Please select users to show more info!");
        }
        alert.show();
    }


}

