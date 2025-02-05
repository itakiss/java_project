package hr.java.project.glavna.fxutil;

import hr.java.project.ProjectApplication;
import hr.java.project.glavna.products.AllProductsScreenController;
import javafx.scene.control.Alert;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public sealed interface UserValidate permits AllProductsScreenController {


    static Boolean isNameValid(String s){
        String regex = "^[a-zA-Z\s\\.]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }




}
