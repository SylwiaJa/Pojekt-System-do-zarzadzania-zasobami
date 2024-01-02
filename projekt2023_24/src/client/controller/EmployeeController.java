package client.controller;

import client.TCPClientFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import server.Employee;


public class EmployeeController {
    private TCPClientFX tcpClientFX;
    private Employee employee;
@FXML
private Label name;
    @FXML
    private Label lastName;
    @FXML
    private Label role;
    @FXML
    private Label zone;

    public EmployeeController(TCPClientFX tcpClientFX, Employee employee) {
        this.tcpClientFX = tcpClientFX;
        this.employee = employee;

    }

@FXML
private void initialize() {
        name.setText("Name: "+employee.getName());
        lastName.setText("Last name: "+employee.getLastName());
        role.setText("Role: "+employee.getRole());
        zone.setText("Zone: "+employee.getZone());
}
    @FXML
    private void employeeButtonAction() {
        tcpClientFX.logOut();
    }


}
