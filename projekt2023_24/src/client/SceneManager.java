package client;

import client.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class SceneManager {
    private Stage primaryStage;
    public SceneManager(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void showLoginScene(TCPClientFX tcpClientFX) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginScene.fxml"));
            LoginController loginController = new LoginController(tcpClientFX);
            loader.setController(loginController);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAdminScene(TCPClientFX tcpClientFX) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminScene.fxml"));
            AdminController adminController = new AdminController(tcpClientFX);
            loader.setController(adminController);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEmployeeScene(TCPClientFX tcpClientFX) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmployeeScene.fxml"));
            EmployeeController employeeController = new EmployeeController(tcpClientFX);
            loader.setController(employeeController);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLeaderScene(TCPClientFX tcpClientFX) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LeaderScene.fxml"));
            LeaderController leaderController = new LeaderController(tcpClientFX);
            loader.setController(leaderController);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showManagerScene(TCPClientFX tcpClientFX) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManagerScene.fxml"));
            ManagerController managerController = new ManagerController(tcpClientFX);
            loader.setController(managerController);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showErrorScene(TCPClientFX tcpClientFX) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ErrorScene.fxml"));
           ErrorController errorController = new ErrorController(tcpClientFX);
           loader.setController(errorController);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


