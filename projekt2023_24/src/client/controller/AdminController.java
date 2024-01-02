package client.controller;

import client.TCPClientFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class AdminController {
private TCPClientFX tcpClientFX;

public AdminController(TCPClientFX tcpClientFX){
    this.tcpClientFX = tcpClientFX;
}
    @FXML
    private Label welcomeLabel;

    @FXML
    private void adminButtonAction() {
       tcpClientFX.logOut();
    }
}
