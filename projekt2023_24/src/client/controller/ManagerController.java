package client.controller;

import client.TCPClientFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import server.Employee;
import server.Order;

import java.util.List;


public class ManagerController {
private TCPClientFX tcpClientFX;
private Employee employee;
private List<Order> orders;
    @FXML
    private Label name;
    @FXML
    private Label lastName;
    @FXML
    private Label role;
    @FXML
    private Label zone;


    public ManagerController(TCPClientFX tcpClientFX, Employee employee, List<Order> orders) {
        this.tcpClientFX = tcpClientFX;
        this.employee = employee;
        this.orders = orders;
    }

    @FXML
    private void initialize() {
        name.setText("Name: "+employee.getName());
        lastName.setText("Last name: "+employee.getLastName());
        role.setText("Role: "+employee.getRole());
        zone.setText("Zone: "+employee.getZone());
    }

    @FXML
    private void managerButtonAction() {
       tcpClientFX.logOut();
    }
}
