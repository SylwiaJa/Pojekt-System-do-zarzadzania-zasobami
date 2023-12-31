package system.demo.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import system.demo.TCPClientFX;
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
