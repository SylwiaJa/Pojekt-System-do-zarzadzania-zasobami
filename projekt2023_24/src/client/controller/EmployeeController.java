package client.controller;

import client.TCPClientFX;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
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
    private VBox taskDetailsVBox;

    private TableView<Task> taskTableView;
    private TableColumn<Task, String> taskNameColumn;
    private TableColumn<Task, Void> actionColumn;

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


            if ("in progress".equalsIgnoreCase(tasks.get(0).getStatus())) {
                Label taskLabel = new Label(
                        "Task: " + tasks.get(0).getName() +
                                "\nDescription: " + tasks.get(0).getDescription() +
                                "\nPriority: " + tasks.get(0).getPriority() +
                                "\nQuantity: " + tasks.get(0).getQuantity() +
                                "\nEquipment Name: " + tasks.get(0).getEquipmentName() +
                                "\nComponent Name: " + tasks.get(0).getComponentName() +
                                "\nStatus: " + tasks.get(0).getStatus()
                );

                Button endButton = new Button("End");
                endButton.setOnAction(event -> endButtonAction(tasks.get(0)));

                taskDetailsVBox.getChildren().add(taskLabel);
                taskDetailsVBox.getChildren().add(endButton);
            } else if ("available".equalsIgnoreCase(tasks.get(0).getStatus())) {
                setupTaskTableView();
            }

    }

    private void setupTaskTableView() {
        taskTableView = new TableView<>();
        taskTableView.setItems(FXCollections.observableArrayList(tasks));

        taskNameColumn = new TableColumn<>("Task Name");
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button acceptButton = new Button("Accept");

            {
                acceptButton.setOnAction(event -> acceptButtonAction(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(acceptButton);
                }
            }
        });

        taskTableView.getColumns().addAll(taskNameColumn, actionColumn);

        taskDetailsVBox.getChildren().add(taskTableView);
    }

    @FXML
    private void endButtonAction(Task task) {
        System.out.println("End button clicked");
    }

    @FXML
    private void acceptButtonAction(Task task) {
        System.out.println("Accept button clicked for task: " + task.getName());
    }

    @FXML
    private void employeeButtonAction() {
        tcpClientFX.logOut();
    }
}
