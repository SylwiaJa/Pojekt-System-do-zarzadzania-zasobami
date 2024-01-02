package system.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import system.demo.controller.LoginController;
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
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
         Object employee = objectInputStream.readObject();
//            switch (employee.getRole()){
//                case "Production Employee":
//                    sceneManager.showEmployeeScene(this);
//                    break;
//                case "Admin":
//                    sceneManager.showAdminScene(this);
//                    break;
//                case "Leader":
//                    sceneManager.showLeaderScene(this);
//                    break;
//                case "Manager":
//                    sceneManager.showManagerScene(this);
//                    break;
//            }

        socket.close();
    }catch (IOException | ClassNotFoundException  e){
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
