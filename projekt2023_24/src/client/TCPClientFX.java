package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.controller.LoginController;
import server.Employee;

import java.io.IOException;
import java.io.ObjectInputStream;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginScene.fxml"));
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
    try {
        Socket socket = new Socket("localhost", 12345);
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username);
        out.println(password);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Employee employee = (Employee) objectInputStream.readObject();
        if (employee!=null){
        switch (employee.getRole()){
            case "Production Employee":
                sceneManager.showEmployeeScene(this, employee);
                break;
            case "Admin":
                sceneManager.showAdminScene(this,employee);
                break;
            case "Leader":
                sceneManager.showLeaderScene(this,employee);
                break;
            case "Manager":
                sceneManager.showManagerScene(this,employee);
                break;
        }}else {
            sceneManager.showErrorScene(this);
        }

        socket.close();
    } catch (IOException | ClassNotFoundException e) {
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
