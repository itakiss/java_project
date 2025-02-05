package hr.java.project.data;

import hr.java.project.entiteti.Change;
import hr.java.project.ProjectApplication;
import hr.java.project.entiteti.KorisnickaUloga;
import hr.java.project.entiteti.User;
import hr.java.project.glavna.fxutil.ChangeWriter;
import hr.java.project.glavna.fxutil.HelperMethods;
import hr.java.project.glavna.fxutil.Notification;
import hr.java.project.iznimke.ObjectExistsException;
import javafx.scene.control.Alert;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


//SVI USERI U BAZI PODATAKA( OVO NAM TREBA )

//SVI USERI U BAZI PODATAKA( OVO NAM TREBA )

public interface UserData {

    String USERS_SERIALIZATION_FILE_NAME = "dat\\users.txt";

    static User getUserFromResult(ResultSet procedureSet) throws SQLException {
        String id = procedureSet.getString("id");
        String password = procedureSet.getString("password");
        String first_name = procedureSet.getString("first_name");
        String last_name = procedureSet.getString("last_name");
        String role = procedureSet.getString("role");

        return new User(id, first_name, last_name, role, password);
    }

    static void createNewUser(String id, String first_name, String last_name, String role, String password) {
        try {
            HelperMethods.checkIfUserExists(id);
        } catch (ObjectExistsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(e.getMessage());
            alert.show();
            ProjectApplication.logger.error(e.getMessage(), e);
        }

        try (Connection conn = DataBase.connectingToDatabase()) {
            PreparedStatement stmnt = conn.prepareStatement("INSERT INTO USERS(id, first_name, last_name, role, password) VALUES(?,?,?,?,?)");
            stmnt.setString(1, id);
            stmnt.setString(2, first_name);
            stmnt.setString(3, last_name);
            stmnt.setString(4, role);
            stmnt.setString(5, password);
            stmnt.executeUpdate();

            writeAllUser(allUserDatabase());

            Change change = new Change(new User("-", "-", "-", null, "-"), UserData.getUserFromId(id));
            ChangeWriter writer = new ChangeWriter(change);
            ProjectApplication.setLoggedUser(UserData.getUserFromId(id));
            writer.addChange(ProjectApplication.getLoggedUser().getRole());



        } catch (IOException | SQLException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    static void updateUser(String id, String password, String first_name, String last_name, String role) {
        try (Connection conn = DataBase.connectingToDatabase()) {
            User oldUser = UserData.getUserFromId(id);

            String sql = "UPDATE USERS SET PASSWORD = ?, FIRST_NAME = ?, LAST_NAME = ?, ROLE = ? WHERE ID = ?";
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, id);
            stmnt.setString(2, first_name);
            stmnt.setString(3, last_name);
            stmnt.setString(4, role);
            stmnt.setString(5, password);
            stmnt.executeUpdate();

            Change change = new Change(oldUser, UserData.getUserFromId(id));
            ChangeWriter writer = new ChangeWriter(change);
            writer.addChange(ProjectApplication.getLoggedUser().getRole());

            writeAllUser(allUserDatabase());

            Notification.updatedSuccessfully("User");

        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    static void deleteUser(String id) {
        Connection conn = null; // Declare the conn variable

        try {
            conn = DataBase.connectingToDatabase();
            User deletedUser = UserData.getUserFromId(id);

            conn.setAutoCommit(false); // Start a transaction

            // Delete from CUSTOMERS table
            PreparedStatement deleteCustomers = conn.prepareStatement("DELETE FROM CUSTOMERS WHERE USER_ID = ?");
            deleteCustomers.setString(1, id);
            deleteCustomers.executeUpdate();

            // Delete from USERS table
            PreparedStatement deleteUser = conn.prepareStatement("DELETE FROM USERS WHERE ID = ?");
            deleteUser.setString(1, id);
            deleteUser.executeUpdate();

            conn.commit(); // Commit the transaction

            Change change = new Change(deletedUser, new User("-", "-", "-", null, "-"));
            ChangeWriter writer = new ChangeWriter(change);
            writer.addChange(ProjectApplication.getLoggedUser().getRole().toString());

            writeAllUser(allUserDatabase());

            Notification.removedSuccessfully("User");

        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
            // Rollback the transaction in case of an exception
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ProjectApplication.logger.error("Error rolling back transaction: " + ex.getMessage(), ex);
                }
            }
        } finally {
            // Close the connection in the finally block
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ProjectApplication.logger.error("Error closing connection: " + ex.getMessage(), ex);
                }
            }
        }
    }



    static void writeAllUser(List<User> users){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_SERIALIZATION_FILE_NAME));
            for(User user : users){
                writer.write(user.getId()+"\n");
                writer.write(DigestUtils.shaHex(user.getPassword()) + "\n");
            }
            writer.close();
        }catch (IOException e){
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    static List<User> allUserDatabase(){
        List<User> userList = new ArrayList<>();

        try(Connection conn = DataBase.connectingToDatabase()) {


            Statement sqlStatement = conn.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery(
                    "SELECT * FROM USERS"
            );

            while(resultSet.next()){
                userList.add(getUserFromResult(resultSet));
            }

        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
        return userList;
    }

    static User getUserFromId(String id) {
        User user = null;

        try (Connection conn = DataBase.connectingToDatabase()) {
            PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
            stmnt.setString(1, id);
            ResultSet resultSet = stmnt.executeQuery();

            if (resultSet.next()) {
                user = getUserFromResult(resultSet);
            }
        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }

        return user;
    }
    static void updateCustomer(String id, String email) {
        try (Connection conn = DataBase.connectingToDatabase()) {
            User oldUser = UserData.getUserFromId(id);

            String sql = "UPDATE CUSTOMERS SET EMAIL = ?,  WHERE ID = ?";
            PreparedStatement stmnt = conn.prepareStatement(sql);
            stmnt.setString(1, email);
            stmnt.setString(2, id);
            stmnt.executeUpdate();


        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }static void createNewCustomer(String id, String first_name, String last_name, String role, String password, String email, String address) {
        try {
            HelperMethods.checkIfUserExists(id);
        } catch (ObjectExistsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(e.getMessage());
            alert.show();
            ProjectApplication.logger.error(e.getMessage(), e);
        }

        try (Connection conn = DataBase.connectingToDatabase()) {
            PreparedStatement stmnt = conn.prepareStatement("INSERT INTO USERS(id, first_name, last_name, role, password) VALUES(?,?,?,?,?)");
            stmnt.setString(1, id);
            stmnt.setString(2, first_name);
            stmnt.setString(3, last_name);
            stmnt.setString(4, role);
            stmnt.setString(5, password);
            stmnt.executeUpdate();

            PreparedStatement customerStmnt = conn.prepareStatement("INSERT INTO CUSTOMERS(USER_ID, email, address) VALUES(?,?,?)");
            customerStmnt.setString(1, id);
            customerStmnt.setString(2, email);
            customerStmnt.setString(3, address);
            customerStmnt.executeUpdate();

            writeAllUser(allUserDatabase());

            Change change = new Change(new User("-", "-", "-", null, "-"), UserData.getUserFromId(id));
            ChangeWriter writer = new ChangeWriter(change);
            ProjectApplication.setLoggedUser(UserData.getUserFromId(id));
            writer.addChange(ProjectApplication.getLoggedUser().getRole().toString());

           // Notification.addedSuccessfully("User");

        } catch (IOException | SQLException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }
    static String getEmailFromUserId(String userId) {
        String email = null;

        try (Connection conn = DataBase.connectingToDatabase()) {
            PreparedStatement stmnt = conn.prepareStatement("SELECT EMAIL FROM CUSTOMERS WHERE USER_ID = ?");
            stmnt.setString(1, userId);
            ResultSet resultSet = stmnt.executeQuery();

            if (resultSet.next()) {
                email = resultSet.getString("EMAIL");
            }
        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }

        return email;
    }

}

