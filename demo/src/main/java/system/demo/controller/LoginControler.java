package system.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import system.demo.PasswordEncryptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LoginControler {
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passField;
    private Button loginButton;

    @FXML
    private void loginButtonAction(){
        String username = nameField.getText();
      //  String password = PasswordEncryptor.encryptPassword(passField.getText());

        try{
            Socket socket = new Socket("localhost",12345);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(username);
          //  out.println(password);
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
