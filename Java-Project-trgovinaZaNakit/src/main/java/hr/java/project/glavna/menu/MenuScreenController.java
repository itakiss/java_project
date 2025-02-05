package hr.java.project.glavna.menu;


import hr.java.project.data.StatsData;
import hr.java.project.entiteti.Stats;
import hr.java.project.ProjectApplication;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public final class MenuScreenController implements StatsData {

    @FXML
    private Text welcomeText;


    @FXML
    public void initialize() {
        String role = String.valueOf(ProjectApplication.getLoggedUser().getRole());
        if (role.equals("ADMIN")) {
            welcomeText.setText("Welcome back admin " + ProjectApplication.getLoggedUser().getSurname());
        } else if (role.equals("KUPAC")) {
            welcomeText.setText("Welcome back "+ProjectApplication.getLoggedUser().getName()+" " + ProjectApplication.getLoggedUser().getSurname());
        } else {
            welcomeText.setText("Welcome back " + ProjectApplication.getLoggedUser().getRole());
        }


    }




}
