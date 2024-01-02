package client.controller;

import client.TCPClientFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


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
