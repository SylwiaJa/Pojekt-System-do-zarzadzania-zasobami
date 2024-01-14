package client.controller;

import client.TCPClientFX;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.*;

import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private TCPClientFX tcpClientFX;
    private Employee employee;
    private List<Employee> employees;
    private List<Task> tasks;
    private List<Order> orders;
    private List<Equipment> equipment;
    private List<Component> components;
    List<String> roles = new ArrayList<>();
    List<String> zones = new ArrayList<>();

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

    public AdminController(TCPClientFX tcpClientFX, Employee employee, List<Employee> employees, List<Task> tasks, List<Order> orders, List<Equipment> equipment, List<Component> components) {
        this.tcpClientFX = tcpClientFX;
        this.employee = employee;
        this.employees = employees;
        this.tasks = tasks;
        this.orders = orders;
        this.equipment = equipment;
        this.components = components;
    }

    public AdminController(TCPClientFX tcpClientFX, Employee employee, List<Employee> employees) {
        this.tcpClientFX = tcpClientFX;
        this.employee = employee;
        this.employees = employees;

    }

    @FXML
    private void initialize() {
        name.setText("Name: " + employee.getName());
        lastName.setText("Last name: " + employee.getLastName());
        role.setText("Role: " + employee.getRole());
        zone.setText("Zone: " + employee.getZone());
        roles = tcpClientFX.getRoles();
         zones = tcpClientFX.getZones();
        // Dodajemy zakładki i ich zawartość
        addEmployeesTab();
       // addTasksTab();
       // addOrdersTab();
      //  addEquipmentsTab();
      //  addComponentsTab();
    }

    private void addEmployeesTab() {
        Tab employeesTab = new Tab("Employees");

        // Tworzymy TableView dla pracowników
        TableView<Employee> employeesTable = new TableView<>();
        employeesTab.setContent(employeesTable);

        // Tworzymy kolumny tabeli
        TableColumn<Employee, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
        TableColumn<Employee, String> roleColumn = new TableColumn<>("Role");
        TableColumn<Employee, String> zoneColumn = new TableColumn<>("Zone");
        TableColumn<Employee, Void> editColumn = new TableColumn<>("Edit");

        // Ustawiamy, jakie wartości mają być wyświetlane w kolumnach
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
        zoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZone()));

        // Ustawiamy przyciski w kolumnie "Edit"
        editColumn.setCellFactory(col -> {
            TableCell<Employee, Void> cell = new TableCell<>() {
                private final Button editButton = new Button("Edit");

                {
                    editButton.setOnAction(event -> {
                        Employee employee = getTableView().getItems().get(getIndex());
                        int employeeId = employee.getId();

                        // Tworzymy nowe okno
                        Stage editStage = new Stage();
                        editStage.setTitle("Edit Employee");

                        // Tworzymy VBox, aby umieścić ComboBoxy, etykiety z danymi pracownika oraz przyciski
                        VBox vbox = new VBox();
                        vbox.setSpacing(10);

                        // ComboBox dla roli
                        ComboBox<String> roleComboBox = new ComboBox<>(FXCollections.observableArrayList(roles));
                        roleComboBox.setValue(employee.getRole()); // Ustawiamy początkową wartość na rolę pracownika

                        // ComboBox dla strefy
                        ComboBox<String> zoneComboBox = new ComboBox<>(FXCollections.observableArrayList(zones));
                        zoneComboBox.setValue(employee.getZone()); // Ustawiamy początkową wartość na strefę pracownika

                        // Etykiety z danymi pracownika
                        vbox.getChildren().add(new Label("Name: " + employee.getName()));
                        vbox.getChildren().add(new Label("Last name: " + employee.getLastName()));

                        // Etykieta i ComboBox dla roli
                        HBox roleBox = new HBox(new Label("Role: "), roleComboBox);
                        vbox.getChildren().add(roleBox);

                        // Etykieta i ComboBox dla strefy
                        HBox zoneBox = new HBox(new Label("Zone: "), zoneComboBox);
                        vbox.getChildren().add(zoneBox);

                        // Przycisk "Apply"
                        Button applyButton = new Button("Apply");
                        applyButton.setOnAction(applyEvent -> {
                            // Pobierz wybrane wartości z ComboBoxów
                            String newRole = roleComboBox.getValue();
                            String newZone = zoneComboBox.getValue();

                            // Zaktualizuj dane pracownika w TableView
                            employee.setRole(newRole);
                            employee.setZone(newZone);
                            tcpClientFX.updateEmployee(employee);
                            TableView<Employee> tableView = getTableView();
                            tableView.refresh();

                            // Zamknij okno po zastosowaniu zmian
                            editStage.close();
                        });

                        // Przycisk "Cancel"
                        Button cancelButton = new Button("Cancel");
                        cancelButton.setOnAction(cancelEvent -> {
                            tcpClientFX.cancel();
                            editStage.close(); // Zamknij okno bez zapisywania zmian
                        });

                        // Dodajemy przyciski do VBox
                        vbox.getChildren().addAll(applyButton, cancelButton);

                        // Dodajemy VBox do sceny
                        Scene editScene = new Scene(vbox, 300, 200);

                        // Ustawiamy scenę w nowym oknie
                        editStage.setScene(editScene);

                        // Pokazujemy nowe okno
                        editStage.show();

                        roles.forEach(System.out::println);
                        zones.forEach(System.out::println);
                        System.out.println("Edit button clicked for employee with ID: " + employeeId);
                    });


                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : editButton);
                }
            };
            return cell;
        });

        // Dodajemy kolumny do tabeli
        employeesTable.getColumns().addAll(idColumn, nameColumn, lastNameColumn, roleColumn, zoneColumn, editColumn);

        // Dodajemy dane do tabeli
        employeesTable.getItems().addAll(employees);

        // Dodajemy zakładkę do TabPane
        tabPane.getTabs().add(employeesTab);
    }


    @FXML
    private void adminButtonAction() {
        tcpClientFX.logOut();
    }
}
