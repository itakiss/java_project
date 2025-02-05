package hr.java.project.glavna.user;


import hr.java.project.data.UserData;
import hr.java.project.entiteti.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.stream.Collectors;

public class AllUsersController   {

    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, String> idColumn, nameColumn, surnameColumn, roleColumn, passwordColumn;
    @FXML
    private TextField searchField;

    @FXML
    ObservableList<User> userObservableList;



    @FXML
    public void initialize(){

        List<User> useri=UserData.allUserDatabase();

        fillTable(useri);


    }
    public void fillTable(List<User> userList){

        userObservableList = FXCollections.observableList(userList);

        idColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getId()));
        nameColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getName()));
        surnameColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getSurname()));
        roleColumn.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getRole()));

        userTableView.setItems(userObservableList);
        userTableView.refresh();
    }

    public void search(){
        String searchText = searchField.getText();

        List<User> filteredUsers = null;


            filteredUsers = UserData.allUserDatabase().stream().filter(user -> user.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                    user.getSurname().toLowerCase().contains(searchText.toLowerCase()) ||
                    user.getId().contains(searchText.toLowerCase()) ||
                    user.getRole().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());



        fillTable(filteredUsers);

    }

}
