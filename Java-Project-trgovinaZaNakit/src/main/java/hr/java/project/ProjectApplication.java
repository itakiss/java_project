package hr.java.project;

import hr.java.project.entiteti.Cart;
import hr.java.project.entiteti.User;
import hr.java.project.threadovi.ShowInfoTitleThread;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class ProjectApplication extends Application {
    public static final Logger logger = LoggerFactory.getLogger(ProjectApplication.class);

    public static Stage mainStage;
    public static User loggedUser;

    public static ExecutorService executorService;
    public static Cart cart;
    public static Stage getStage() {
        return mainStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        executorService = Executors.newCachedThreadPool();

        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectApplication.class.getResource("/fxml/menus/loginScreen.fxml"));
        Parent root = fxmlLoader.load();

        // Create a new Pane and set it as the root
        Pane rootPane = new Pane(root);

        Scene scene = new Scene(rootPane, 1280, 800);

        // Load the background image
        Image backgroundImage = new Image(new FileInputStream("src/main/resources/fxml/images/purple_clouds.jpg"));
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        // Set the background image to the rootPane
        rootPane.setBackground(new Background(background));

        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/fxml/images/purple_clouds.jpg")));
        stage.show();

        Timeline threadSat = new Timeline(new KeyFrame(Duration.millis(1), event -> Platform.runLater(new ShowInfoTitleThread())));
        threadSat.setCycleCount(Timeline.INDEFINITE);
        threadSat.play();


    }

    public static void setMainPage(BorderPane root) {
        Scene scene = new Scene(root, 1280, 800);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.setFullScreen(false);
        mainStage.show();
    }

    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}