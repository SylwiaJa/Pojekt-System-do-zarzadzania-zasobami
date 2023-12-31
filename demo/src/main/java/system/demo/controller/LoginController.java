package system.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import system.demo.PasswordEncryptor;
import system.demo.TCPClientFX;


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
