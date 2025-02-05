module Java.project.ivkovic {
    requires javafx.graphics;
    requires org.slf4j;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires commons.codec;
    requires java.mail;

    opens hr.java.project to javafx.fxml;
    exports hr.java.project.data;
    opens hr.java.project.data to javafx.fxml;
    exports hr.java.project.entiteti;
    opens hr.java.project.entiteti to javafx.fxml;
    exports hr.java.project.glavna.menu;
    opens hr.java.project.glavna.menu to javafx.fxml;
    exports hr.java.project.glavna.user;
    opens hr.java.project.glavna.user to javafx.fxml;
    exports hr.java.project;
    exports hr.java.project.glavna.products;
    opens hr.java.project.glavna.products to javafx.fxml;
    exports hr.java.project.glavna.fxutil;
    opens hr.java.project.glavna.fxutil to javafx.fxml;
    exports hr.java.project.glavna.changes;
    opens hr.java.project.glavna.changes to javafx.fxml;
    exports hr.java.project.glavna.cart;
    opens hr.java.project.glavna.cart to javafx.fxml;
    exports hr.java.project.glavna.profile;
    opens hr.java.project.glavna.profile to javafx.fxml;

}