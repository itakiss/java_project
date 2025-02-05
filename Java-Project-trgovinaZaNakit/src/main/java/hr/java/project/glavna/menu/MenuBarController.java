package hr.java.project.glavna.menu;


import hr.java.project.ProjectApplication;
import hr.java.project.entiteti.KorisnickaUloga;
import hr.java.project.entiteti.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MenuBarController {

    @FXML
    private Menu products;
    @FXML
    private MenuItem addProducts, editProducts, allProducts;
    @FXML
    private Menu users;
    @FXML
    private MenuItem addUsers, allUsers, editUsers;

    @FXML
    private Menu bills;
    @FXML
    private MenuItem allBills;

    @FXML
    private Menu cart;

    @FXML
    private Menu profile;

    @FXML
    private MenuItem logout, viewProfile, exitApplication, cartUser;

    @FXML
    private Menu admin;

    @FXML
    private  MenuItem userChanges,  productChanges;


    @FXML
    public void initialize(){
        User currentUser = ProjectApplication.getLoggedUser();
        if(currentUser.getRole().equals(KorisnickaUloga.KUPAC.toString())){
            // Disable and hide menu items for customers
            addUsers.setDisable(true);
            allUsers.setDisable(true);
            editUsers.setDisable(true);
            addProducts.setDisable(true);
            editProducts.setDisable(true);
            userChanges.setDisable(true);
            productChanges.setDisable(true);
        }else if(currentUser.getRole().equals(KorisnickaUloga.ADMIN.toString())){
            // Disable and hide menu items for admin
            cartUser.setDisable(true);
        }
    }
    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ProjectApplication.setLoggedUser(null);

            Stage mainStage = ProjectApplication.getStage();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/loginScreen.fxml"));
                Parent loginScreen = fxmlLoader.load();
                mainStage.setScene(new Scene(loginScreen, 1280, 800));
                mainStage.setResizable(false);
                mainStage.setFullScreen(false);
                mainStage.show();
            } catch (IOException e) {
                ProjectApplication.logger.error(e.getMessage(), e);
            }
        }
    }

    public void exitApplication(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to exit application?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
    }



    public void showAddProductScreen() {
        BorderPane root;
        try {
            root = FXMLLoader.load(
                    getClass().getResource("/fxml/products/addProductScreen.fxml"));
            ProjectApplication.setMainPage(root);
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    public void showAllProductsScreen() {
        BorderPane root;
        try {
            root = FXMLLoader.load(
                    getClass().getResource("/fxml/products/allProductScreen.fxml"));
            ProjectApplication.setMainPage(root);
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    public void showEditProductScreen() {
        BorderPane root;
        try {
            root = FXMLLoader.load(
                    getClass().getResource("/fxml/products/editProductScreen.fxml"));
            ProjectApplication.setMainPage(root);
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    public void showAddUserScreen() {
        BorderPane root;
        try {
            root =  FXMLLoader.load(getClass().getResource("/fxml/users/addUserScreen.fxml"));
            ProjectApplication.setMainPage(root);
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    public void showAllUserScreen() {
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/users/allUsersScreen.fxml"));
            ProjectApplication.setMainPage(root);
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }



    public void showEditUserScreen() {
        BorderPane root;
        try {
            root = FXMLLoader.load(
                    getClass().getResource("/fxml/users/editUserScreen.fxml"));
            ProjectApplication.setMainPage(root);
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    public void showCartScreen() {
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/cart/cartScreen.fxml"));
            ProjectApplication.setMainPage(root);
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    public void showAllBillsScreen() {
        BorderPane root;
        try {
            root = FXMLLoader.load(
                    getClass().getResource("/fxml/bills/allBillsScreen.fxml"));
            ProjectApplication.setMainPage(root);
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    public void showOrdersScreen() {
        BorderPane root;
        try {
            root = FXMLLoader.load(
                    getClass().getResource("/fxml/profile/allOrdersScreen.fxml"));
            ProjectApplication.setMainPage(root);
        } catch (IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    public void showViewProfileScreen() {
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/profile/viewProfileScreen.fxml"));
            ProjectApplication.setMainPage(root);
        }catch (IOException e){
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }


    public void showChangesUsersScreen(){
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/admin/changesUsersScreen.fxml"));
            ProjectApplication.setMainPage(root);
        }catch (IOException e){
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    public void showChangesProductsScreen(){
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/admin/changesProductScreen.fxml"));
            ProjectApplication.setMainPage(root);
        }catch (IOException e){
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }

    public void showChangesOrdersScreen(){
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/admin/changesOrdersScreen.fxml"));
            ProjectApplication.setMainPage(root);
        }catch (IOException e){
            ProjectApplication.logger.error(e.getMessage(), e);
        }
    }


}
