package hr.java.project.glavna.fxutil;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class FXUtil {
    public static void showDataSourceErrorAlert(String message) {
        var alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        alert.showAndWait();
    }
}
