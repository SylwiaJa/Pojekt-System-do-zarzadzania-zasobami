module system.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens client to javafx.fxml;
    exports client;
    exports client.controller;
    opens client.controller to javafx.fxml;
    opens server to javafx.base, javafx.fxml;

}