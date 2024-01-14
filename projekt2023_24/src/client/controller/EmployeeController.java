package client.controller;

import client.TCPClientFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.Employee;
import server.Task;

import java.util.List;

public class EmployeeController {

    private TCPClientFX tcpClientFX;
    private Employee employee;
    private List<Task> tasks;

    @FXML
    private Label name;
    @FXML
    private Label lastName;
    @FXML
    private Label role;
    @FXML
    private Label zone;

    @FXML
    private TabPane tabPane;

    public EmployeeController(TCPClientFX tcpClientFX, Employee employee, List<Task> tasks) {
        this.tcpClientFX = tcpClientFX;
        this.employee = employee;
        this.tasks = tasks;
    }

    @FXML
    private void initialize() {
        name.setText("Name: " + employee.getName());
        lastName.setText("Last name: " + employee.getLastName());
        role.setText("Role: " + employee.getRole());
        zone.setText("Zone: " + employee.getZone());


    }
    @FXML
    private void employeeButtonAction() {
        tcpClientFX.logOut();
    }
}
