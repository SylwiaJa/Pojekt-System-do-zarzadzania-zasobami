package client;

import client.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.*;


import java.io.IOException;
import java.util.List;

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

    public void showAdminScene(TCPClientFX tcpClientFX, Employee employee, List<Employee> employees) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminScene.fxml"));
            AdminController adminController = new AdminController(tcpClientFX,employee, employees);
            loader.setController(adminController);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEmployeeScene(TCPClientFX tcpClientFX, Employee employee, List<Task> tasks) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmployeeScene.fxml"));
            EmployeeController employeeController = new EmployeeController(tcpClientFX,employee, tasks);
            loader.setController(employeeController);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLeaderScene(TCPClientFX tcpClientFX, Employee employee, List<Task> tasks, List<Employee> employees, List<Equipment> equipmentList) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LeaderScene.fxml"));
            LeaderController leaderController = new LeaderController(tcpClientFX,employee, tasks, employees, equipmentList);
            loader.setController(leaderController);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showManagerScene(TCPClientFX tcpClientFX, Employee employee, List<Order> orders, List<Task> tasks, List<Employee> employees, List<Equipment> equipmentList, List<String> taskCategories, List<String> equipmentCategories, List<License> licenseList, List<String> zoneList) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManagerScene.fxml"));
            ManagerController managerController = new ManagerController(tcpClientFX,employee,orders, tasks,employees,equipmentList,taskCategories,equipmentCategories,licenseList,zoneList);
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
    public void closeApp(){
        primaryStage.close();
    }
}


