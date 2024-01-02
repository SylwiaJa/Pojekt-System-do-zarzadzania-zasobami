package client.controller;

import client.PasswordEncryptor;
import client.TCPClientFX;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private String username;
    private String password;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passField;
    private Button loginButton;
    private TCPClientFX tcpClientFX;

    public LoginController(TCPClientFX tcpClientFX){
        this.tcpClientFX = tcpClientFX;
    }

    @FXML
    private void loginButtonAction(){
        this.username = nameField.getText();
        this.password = PasswordEncryptor.encryptPassword(passField.getText());
        tcpClientFX.setLoginData(username,password);
    }
}
