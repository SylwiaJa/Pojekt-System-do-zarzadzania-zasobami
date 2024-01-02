package client.controller;
import client.TCPClientFX;
import javafx.fxml.FXML;

public class ErrorController {
    private TCPClientFX tcpClientFX;

    public ErrorController(TCPClientFX tcpClientFX) {
        this.tcpClientFX = tcpClientFX;
    }
@FXML
    private void errorButtonAction(){
        tcpClientFX.logOut();
    }
}
