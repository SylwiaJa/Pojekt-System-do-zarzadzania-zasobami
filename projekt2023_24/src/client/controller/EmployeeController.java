package client.controller;

import client.TCPClientFX;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import server.Employee;
import server.Task;

import java.util.List;
import java.util.Optional;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("End Task");
        alert.setHeaderText("Task: " + task.getName()+" Quantity: " + task.getQuantity());

        // Tworzenie pól tekstowych do wpisania QuantityOK i QuantityNOK
        TextField quantityOKField = new TextField();
        quantityOKField.setPromptText("QuantityOK");

        TextField quantityNOKField = new TextField();
        quantityNOKField.setPromptText("QuantityNOK");

        GridPane grid = new GridPane();
        grid.add(new Label("Enter QuantityOK:"), 0, 0);
        grid.add(quantityOKField, 1, 0);
        grid.add(new Label("Enter QuantityNOK:"), 0, 1);
        grid.add(quantityNOKField, 1, 1);

        alert.getDialogPane().setContent(grid);

        ButtonType acceptButton = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(acceptButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == acceptButton) {
            String quantityOK = quantityOKField.getText();
            String quantityNOK = quantityNOKField.getText();

            try {
                int intQuantityOK = Integer.parseInt(quantityOK);
                int intQuantityNOK = Integer.parseInt(quantityNOK);

                if (intQuantityOK + intQuantityNOK != task.getQuantity()) {
                    // Wyświetlenie okna z błędem, gdy suma ilości QuantityOK i QuantityNOK nie zgadza się z Quantity zadania.
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Incorrect Quantities");
                    errorAlert.setContentText("The sum of QuantityOK and QuantityNOK should be equal to the Task's Quantity.");
                    errorAlert.showAndWait();
                } else {
                   tcpClientFX.endTask(task);
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setTitle("Task Completed");
                    infoAlert.setHeaderText("Task has been completed successfully.");
                    infoAlert.setContentText("Please log in again.");
                    infoAlert.showAndWait();
                    tcpClientFX.logOut();
                }
            } catch (NumberFormatException e) {
                // Wyświetlenie okna z błędem, gdy wprowadzone ilości nie są liczbami.
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Invalid Input");
                errorAlert.setContentText("Please enter valid numeric values for QuantityOK and QuantityNOK.");
                errorAlert.showAndWait();
            }
        }
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
