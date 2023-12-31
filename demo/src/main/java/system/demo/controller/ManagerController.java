import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ManagerController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void managerButtonAction() {
        // Obsługa akcji przycisku w scenie menedżera
        System.out.println("Akcja przycisku w scenie menedżera");
    }
}
