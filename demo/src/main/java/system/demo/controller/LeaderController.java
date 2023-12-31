package system.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LeaderController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void leaderButtonAction() {
        // Obsługa akcji przycisku w scenie lidera
        System.out.println("Akcja przycisku w scenie lidera");
    }
}
