package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.controller.LoginController;
import server.Employee;
import server.Equipment;
import server.Order;
import server.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TCPClientFX extends Application {
    private String username;
    private String password;
    private SceneManager sceneManager;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;



    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginScene.fxml"));
            LoginController loginController = new LoginController(this);
            loader.setController(loginController);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setTitle("Welcome");
            primaryStage.setScene(scene);
            primaryStage.show();

            sceneManager = new SceneManager(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLoginData(String username, String password) {

        this.username = username;
        this.password = password;
        try {
            socket = new Socket("localhost", 12345);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream  =new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(username);
            objectOutputStream.writeObject(password);


            Employee employee = (Employee) objectInputStream.readObject();
            if (employee != null) {
                switch (employee.getRole()) {
                    case "Production Employee":
                       List<Task> employeeTasks = (List<Task>) objectInputStream.readObject();
                        sceneManager.showEmployeeScene(this, employee,employeeTasks);
                        break;
                    case "Admin": {
                        List<Employee> employees = (List<Employee>) objectInputStream.readObject();
                        sceneManager.showAdminScene(this, employee, employees);
                        break;
                    }
                    case "Leader":
                        sceneManager.showLeaderScene(this, employee);
                        break;
                    case "Manager": {
                        List<Order> orders = (List<Order>) objectInputStream.readObject();
                        List<Task> tasks = (List<Task>) objectInputStream.readObject();
                        List<Employee> employees = (List<Employee>) objectInputStream.readObject();
                        List<Equipment> equipmentList = (List<Equipment>) objectInputStream.readObject();
                        sceneManager.showManagerScene(this, employee, orders, tasks,employees,equipmentList);
                        break;
                    }
                }
            } else {
                sceneManager.showErrorScene(this);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void logOut() {

        try {
            objectOutputStream.writeObject("Close");
            // Zamknij gniazdo i strumienie wejścia/wyjścia
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sceneManager.showLoginScene(this);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public List<String> getRoles() {
        try {
            return  (List<String>) objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
    public void updateEmployee(Employee employee) {

        try {
            objectOutputStream.writeObject("Update");
            objectOutputStream.writeObject(employee);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public  void showLogin() {
        sceneManager.showLoginScene(this);
    }
    public List<String> getZones() {
        try {
            return  (List<String>) objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public void cancel() {
        try {


            objectOutputStream.writeObject("Cancel");
        }catch (IOException e){
            e.printStackTrace();
        }
        }

    public List<Object> getOrderInfo(Order order) {
        List<Object> objects=new ArrayList<>();
        try {
            objectOutputStream.writeObject("orders");
            objectOutputStream.writeObject(order);
            objects = (List<Object>) objectInputStream.readObject();

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return objects ;
    }

    public void addTask(Task task) {
        try {
            objectOutputStream.writeObject("addTask");
            objectOutputStream.writeObject(task);
        }catch (IOException e){
            e.printStackTrace();
        }
        }

    public List<List<String>> getUseEquipment(int id) {
        try {
            objectOutputStream.writeObject("getUseEquipment");
            objectOutputStream.writeObject(id);
            List<List<String>> useEquipment = (List<List<String>>) objectInputStream.readObject();
            return useEquipment;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateEquipment(Equipment equipment) {
        try{
            objectOutputStream.writeObject("updateEquipment");
            objectOutputStream.writeObject(equipment);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
