package system.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import system.demo.TCPClientFX;

public class EmployeeController {
    private TCPClientFX tcpClientFX;

    public EmployeeController(TCPClientFX tcpClientFX) {
        this.tcpClientFX = tcpClientFX;
    }

    @FXML
    private Label welcomeLabel;

    @FXML
    private void employeeButtonAction() {
        tcpClientFX.logOut();
    }
}
