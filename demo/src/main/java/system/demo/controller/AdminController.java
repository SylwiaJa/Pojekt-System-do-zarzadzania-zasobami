package system.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void adminButtonAction() {
        // Obsługa akcji przycisku w scenie administratora
        System.out.println("Akcja przycisku w scenie administratora");
    }
}
