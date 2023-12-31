package system.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmployeeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void employeeButtonAction() {
        // Obsługa akcji przycisku w scenie pracownika
        System.out.println("Akcja przycisku w scenie pracownika");
    }
}
