package hr.java.project.glavna.fxutil;

import hr.java.project.data.*;

import hr.java.project.entiteti.User;
import hr.java.project.ProjectApplication;
import hr.java.project.iznimke.ObjectExistsException;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface HelperMethods {
    /**
     * This method is used to validate a string if it is an Full Name or not.
     * {@link}              https://stackoverflow.com/a/35458020

     * @return boolean      Returns true or false.
     * @since               1.0.0
     */
    public static boolean validateName(String name) {
//      ^                       # start of string
//      [a-zA-Z]{3,}            # 3 or more ASCII letters
//      $                       # end of string.
        Matcher matcher = Pattern.compile("^[a-zA-Z]{3,}$").matcher(name);
        return matcher.matches();
    }

    /**
     * This method is used to validate a string if it is a username or not.
     * {@link}              https://stackoverflow.com/a/6782475
     * @param username      Accepts a string to validate as email address.
     * @return boolean      Returns true or false.
     * @since               1.0.0
     */
    public static boolean validateUsername(String username) {
//      ^                       # start of string
//      [a-zA-Z]                # lowercase or uppercase ASCII letters
//      \\w{4, 29}              # remaining items are word items, which includes the underscore,
//      until it reaches the end and that is represented with $
//      {4, 29}                 # 5-30 character constraint given, minus the predefined first character
        Matcher matcher = Pattern.compile("^[A-Za-z]\\w{4,29}$", Pattern.CASE_INSENSITIVE).matcher(username);
        return matcher.find();
    }

    /**
     * This method is used to validate a string if it is an email address or not.
     * {@link}              https://stackoverflow.com/a/8204716
     * @param emailStr      Accepts an string to validate as email address.
     * @return boolean      Returns true or false.
     * @since               1.0.0
     */
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(emailStr);
        return matcher.find();
    }

    /**
     * This method is used to validate a string if it complies with
     * required password specifications or not.
     * {@link}              https://stackoverflow.com/a/3802238
     * @param password      Accepts a string to validate as password.
     * @return boolean      Returns true or false.
     * @since               1.0.0
     */
    public static boolean validatePassword(String password){
//        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
//        ^                 # start-of-string
//        (?=.*[0-9])       # a digit must occur at least once
//        (?=.*[a-z])       # a lower case letter must occur at least once
//        (?=.*[A-Z])       # an upper case letter must occur at least once
//        (?=.*[@#$%^&+=])  # a special character must occur at least once
//        (?=\S+$)          # no whitespace allowed in the entire string
//        .{8,}             # anything, at least eight places though
//        $                 # end-of-string
        return password.matches("^.{6,16}$");
    }

    public static void checkIfUserExists(String id) throws ObjectExistsException {
        List<User> productList = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "mivkovic", "java");

            Statement sqlStatement = conn.createStatement();
            ResultSet proceduresResultSet = sqlStatement.executeQuery(
                    "SELECT * FROM USERS WHERE ID='" + id + "'"
            );

            while (proceduresResultSet.next()) {
                User newPatient = UserData.getUserFromId(id);
                productList.add(newPatient);
            }

            conn.close();

        } catch (SQLException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }

        if (productList.size() > 0) {
            throw new ObjectExistsException("User already exists in system!");
        }
    }



    static Boolean isBeforeToday(LocalDateTime dateTimeValue) {
        if (dateTimeValue.isBefore(LocalDateTime.now())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setHeaderText("Date is not valid!");
            alert.setContentText("Checkup date needs to be after today's date!");
            alert.show();
            return true;
        } else {
            return false;
        }

    }

    public static void alertBox(String message, String header, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.showAndWait();
    }
}

