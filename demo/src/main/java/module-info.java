module system.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens system.demo to javafx.fxml;
    exports system.demo;
    exports system.demo.controller;
    opens system.demo.controller to javafx.fxml;
}