package system.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import system.demo.TCPClientFX;

public class ManagerController {
private TCPClientFX tcpClientFX;

    public ManagerController(TCPClientFX tcpClientFX) {
        this.tcpClientFX = tcpClientFX;
    }

    @FXML
    private Label welcomeLabel;

    @FXML
    private void managerButtonAction() {
       tcpClientFX.logOut();
    }
}
