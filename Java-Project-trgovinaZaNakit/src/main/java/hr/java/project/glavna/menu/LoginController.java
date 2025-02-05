package hr.java.project.glavna.menu;


import hr.java.project.data.DataBase;
import hr.java.project.data.UserData;
import hr.java.project.ProjectApplication;
import hr.java.project.entiteti.User;
import hr.java.project.iznimke.NotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginController implements UserData {

    private static final String USERS_SERIALIZATION_FILE_NAME = "dat\\users.txt";

    private Map<String, String> users;

    @FXML
    private TextField idTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Text errorText;

    @FXML
    public void initialize(){
        users = new HashMap<>();
        try(Scanner scanner = new Scanner(new File(USERS_SERIALIZATION_FILE_NAME))){
            while(scanner.hasNextLine()){

            String id = scanner.nextLine();
            String password = scanner.nextLine();

            users.put(id, password);
            }
        } catch (FileNotFoundException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }
    public void login() {

        String inputIdText = idTextField.getText();
        String inputPasswordText = passwordTextField.getText();

        String hashedPassword = DigestUtils.shaHex(inputPasswordText);

        try{
            if(users.containsKey(inputIdText) && users.get(inputIdText).equals(hashedPassword)){
                errorText.setText("");
                ProjectApplication.setLoggedUser(getUser(inputIdText));
                BorderPane root;
                try {
                    root = FXMLLoader.load(
                            getClass().getResource("/fxml/menus/menuScreen.fxml"));
                    ProjectApplication.setMainPage(root);
                } catch (IOException e) {
                    ProjectApplication.logger.error(e.getMessage(), e);
                }
            }else{
                throw new NotFoundException("User not found!");
            }
        }catch (NotFoundException e){
            ProjectApplication.logger.error(e.getMessage(), e);
            errorText.setText("User not found!");
            idTextField.setText("");
            passwordTextField.setText("");
        }



    }
    public User getUser(String id) {
        User userToSet = null;
        try (Connection conn = DataBase.connectingToDatabase()) {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, id);
            ResultSet userResultSet = statement.executeQuery();

            if (userResultSet.next()) {
                userToSet = UserData.getUserFromResult(userResultSet);
            }

        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }

        return userToSet;
    }


    public void handleRegisterButtonAction(ActionEvent actionEvent) throws IOException {
        BorderPane root;
        try {
            root = FXMLLoader.load(
                    getClass().getResource("/fxml/menus/registerScreen.fxml"));
            ProjectApplication.setMainPage(root);
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }

    }
}