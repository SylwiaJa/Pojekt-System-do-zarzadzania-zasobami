package system.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import system.demo.controller.LoginController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClientFX extends Application {
private String username;
private String password;
    private  SceneManager sceneManager;
    @Override
    public void start(Stage primaryStage) throws InterruptedException {
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
            LoginController loginControler = new LoginController(this);
            loader.setController(loginControler);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Welcome");
            primaryStage.setScene(scene);
            primaryStage.show();
        sceneManager = new SceneManager(primaryStage);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
public void setLoginData(String username, String password) {
    this.username = username;
    this.password = password;
    try{
        Socket socket = new Socket("localhost",12345);
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        out.println(username);
        out.println(password);
        String role = in.nextLine();
        System.out.println(role);
        if(role.equals("admin"))
        sceneManager.showAdminScene(this);
        else if (role.equals("manager"))
            sceneManager.showManagerScene(this);
        else if (role.equals("leader"))
            sceneManager.showLeaderScene(this);
        else if (role.equals("employee"))
            sceneManager.showEmployeeScene(this);
        else
            sceneManager.showErrorScene(this);
        socket.close();
    }catch (IOException e){
        e.printStackTrace();
    }
}
public void logOut(){
        sceneManager.showLoginScene(this);
}

    public static void main(String[] args) {
        launch(args);
    }
}
