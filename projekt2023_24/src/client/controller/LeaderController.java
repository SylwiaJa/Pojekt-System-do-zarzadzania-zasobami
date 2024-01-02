package client.controller;

import client.TCPClientFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class LeaderController {
private TCPClientFX tcpClientFX;

    public LeaderController(TCPClientFX tcpClientFX) {
        this.tcpClientFX = tcpClientFX;
    }

    @FXML
    private Label welcomeLabel;

    @FXML
    private void leaderButtonAction() {
        tcpClientFX.logOut();
    }
}
