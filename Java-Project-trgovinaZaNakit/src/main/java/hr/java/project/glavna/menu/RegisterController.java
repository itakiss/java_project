package hr.java.project.glavna.menu;

import hr.java.project.ProjectApplication;

import hr.java.project.data.UserData;
import hr.java.project.entiteti.KorisnickaUloga;
import hr.java.project.entiteti.User;
import hr.java.project.glavna.fxutil.HelperMethods;
import hr.java.project.threadovi.ShowInfoTitleThread;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class RegisterController implements hr.java.project.glavna.fxutil.Notification {

    private static final String USERS_SERIALIZATION_FILE_NAME = "dat\\users.txt";
    @FXML
    public TextField firstNameField, lastNameField, addressField, idField, emailField;

    @FXML
    public PasswordField passwordField;

    private Map<String, String> kupci;
    Stage dialogStage = new Stage();
    Scene scene;

    public void initialize(){
        kupci = new HashMap<>();
        try(Scanner scanner = new Scanner(new File(USERS_SERIALIZATION_FILE_NAME))){
            while(scanner.hasNextLine()){

                String id = scanner.nextLine();
                String password = DigestUtils.shaHex(scanner.nextLine());

                kupci.put(id, password);
            }
        } catch (FileNotFoundException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }


    public void handleLoginButtonAction(ActionEvent actionEvent) throws IOException {

        Stage dialogStage;
        Node node = (Node) actionEvent.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/menus/loginScreen.fxml")));
        dialogStage.setScene(scene);
        dialogStage.show();

        Timeline threadSat = new Timeline(new KeyFrame(Duration.millis(1), event -> Platform.runLater(new ShowInfoTitleThread())));
        threadSat.setCycleCount(Timeline.INDEFINITE);
        threadSat.play();
    }


    public void handleRegisterButtonAction(ActionEvent actionEvent) {
        String validationErrors = "";
        boolean errors = false;
        final String firstName = firstNameField.getText();
        final String lastName = lastNameField.getText();
        final String id = idField.getText();
        final String email = emailField.getText();
        final String address = addressField.getText();
        String providedPassword = passwordField.getText();

        // Validate First Name
        if (firstName == null || firstName.isEmpty()) {
            validationErrors += "Please enter your First Name! \n";
            errors = true;
        } else if (!HelperMethods.validateName(firstName)) {
            validationErrors += "Please enter a valid First Name! (at least 3 letters) \n";
            errors = true;
        }

        // Validate Last Name
        if (lastName == null || lastName.isEmpty()) {
            validationErrors += "Please enter your Last Name! \n";
            errors = true;
        } else if (!HelperMethods.validateName(lastName)) {
            validationErrors += "Please enter a valid Last Name! (at least 3 letters) \n";
            errors = true;
        }

        // Validate Username
        if (id == null || id.isEmpty()) {
            validationErrors += "Please enter a Username! \n";
            errors = true;
        } else if (!HelperMethods.validateUsername(id)) {
            validationErrors += "Please enter a valid Username! (start with a letter, 5-30 characters) \n";
            errors = true;
        }

        // Validate Email
        if (email == null || email.isEmpty()) {
            validationErrors += "Please enter an Email address! \n";
            errors = true;
        } else if (!HelperMethods.validateEmail(email)) {
            validationErrors += "Please enter a valid Email address! (example: example@example.com) \n";
            errors = true;
        }

        // Validate Address
        if (address == null || address.isEmpty()) {
            validationErrors += "Please enter your Address! \n";
            errors = true;
        }

        // Validate Password
        if (providedPassword == null || providedPassword.isEmpty()) {
            validationErrors += "Please enter a Password! \n";
            errors = true;
        } else if (!HelperMethods.validatePassword(providedPassword)) {
            validationErrors += "Please enter a valid Password! (6-16 characters, at least one uppercase, one lowercase, one digit, and one special character) \n";
            errors = true;
        }

        if (errors) {
            HelperMethods.alertBox(validationErrors, null, "Registration Failed!");
        }

        else {






            Task<Void> addKupacTask = new Task<Void>() {
                @Override
                protected Void call() {
                    synchronized (kupci) {
                        UserData.createNewCustomer(id, firstName, lastName, String.valueOf(KorisnickaUloga.KUPAC), providedPassword, email, address);
                    }
                    return null;
                }
            };

            addKupacTask.setOnSucceeded(e -> {
                User kupac = UserData.getUserFromId(id);

                if (kupac != null) {
                    UserSessionController.setUserId(kupac.getId());
                    UserSessionController.setUserFullName(kupac.getName());
                    UserSessionController.setUserName(kupac.getSurname());
                    UserSessionController.setUserAdmin(kupac.getRole());

                    Node node = (Node) actionEvent.getSource();
                    dialogStage = (Stage) node.getScene().getWindow();
                    dialogStage.close();
                    BorderPane root;

                    if (kupac.getRole().equals("KUPAC")) {
                        try {
                            scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/menus/menuScreen.fxml")));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else if (kupac.getRole().equals("ADMIN")) {
                        try {
                            scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/menus/menuScreen.fxml")));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }

                    dialogStage.setScene(scene);
                    dialogStage.show();

                }
            });

            new Thread(addKupacTask).start();
        }

    }
}