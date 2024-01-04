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

import java.util.List;

public class AdminController {
    private TCPClientFX tcpClientFX;
    private Employee employee;
    private List<Employee> employees;
    private List<Task> tasks;
    private List<Order> orders;
    private List<Equipment> equipment;
    private List<Component> components;

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
                        List<String> roles = tcpClientFX.getRoles();
                        List<String> zones = tcpClientFX.getZones();

                        // Tworzymy nowe okno
                        Stage editStage = new Stage();
                        editStage.setTitle("Edit Employee");

                        // Tworzymy VBox, aby umieścić ComboBoxy i etykiety z danymi pracownika
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


//    private void addTasksTab() {
//        Tab tasksTab = new Tab("Tasks");
//
//        // Tworzymy TableView dla zadań
//        TableView<Task> tasksTable = new TableView<>();
//        tasksTab.setContent(tasksTable);
//
//        // Tworzymy kolumny tabeli
//        TableColumn<Task, Integer> idColumn = new TableColumn<>("ID");
//        TableColumn<Task, String> nameColumn = new TableColumn<>("Name");
//        TableColumn<Task, String> priorityColumn = new TableColumn<>("Priority");
//        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Description");
//        TableColumn<Task, String> normColumn = new TableColumn<>("Norm");
//        TableColumn<Task, String> componentColumn = new TableColumn<>("Component");
//        TableColumn<Task, String> equipmentColumn = new TableColumn<>("Equipment");
//        TableColumn<Task, String> statusColumn = new TableColumn<>("Status");
//        TableColumn<Task, String> timeStartColumn = new TableColumn<>("Time Start");
//        TableColumn<Task, String> timeEndColumn = new TableColumn<>("Time End");
//        TableColumn<Task, String> employeeColumn = new TableColumn<>("Employee");
//
//        // Ustawiamy, jakie wartości mają być wyświetlane w kolumnach
//        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
//        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
//        priorityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPriority()));
//        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
//        normColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNorm()));
//        componentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComponent()));
//        equipmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEquipment()));
//        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
//        timeStartColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTimeStart()));
//        timeEndColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTimeEnd()));
//        employeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee()));
//
//        // Dodajemy kolumny do tabeli
//        tasksTable.getColumns().addAll(idColumn, nameColumn, priorityColumn, descriptionColumn, normColumn, componentColumn, equipmentColumn, statusColumn, timeStartColumn, timeEndColumn, employeeColumn);
//
//        // Dodajemy dane do tabeli
//        tasksTable.getItems().addAll(tasks);
//
//        // Dodajemy zakładkę do TabPane
//        tabPane.getTabs().add(tasksTab);
//    }

//    private void addOrdersTab() {
//        Tab ordersTab = new Tab("Orders");
//
//        // Tworzymy TableView dla zamówień
//        TableView<Order> ordersTable = new TableView<>();
//        ordersTab.setContent(ordersTable);
//
//        // Tworzymy kolumny tabeli
//        TableColumn<Order, Integer> idColumn = new TableColumn<>("ID");
//        TableColumn<Order, String> nameColumn = new TableColumn<>("Name");
//        TableColumn<Order, Integer> quantityOrderedColumn = new TableColumn<>("Quantity Ordered");
//        TableColumn<Order, Integer> quantityInProductionColumn = new TableColumn<>("Quantity In Production");
//        TableColumn<Order, Integer> quantityFinishedColumn = new TableColumn<>("Quantity Finished");
//        TableColumn<Order, String> statusColumn = new TableColumn<>("Status");
//
//        // Ustawiamy, jakie wartości mają być wyświetlane w kolumnach
//        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
//        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
//        quantityOrderedColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getQuantityOrdered()).asObject());
//        quantityInProductionColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getQuantityInProduction()).asObject());
//        quantityFinishedColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getQuantityFinished()).asObject());
//        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
//
//        // Dodajemy kolumny do tabeli
//        ordersTable.getColumns().addAll(idColumn, nameColumn, quantityOrderedColumn, quantityInProductionColumn, quantityFinishedColumn, statusColumn);
//
//        // Dodajemy dane do tabeli
//        ordersTable.getItems().addAll(orders);
//
//        // Dodajemy zakładkę do TabPane
//        tabPane.getTabs().add(ordersTab);
//    }

//    private void addEquipmentsTab() {
//        Tab equipmentsTab = new Tab("Equipments");
//
//        // Tworzymy TableView dla sprzętu
//        TableView<Equipment> equipmentsTable = new TableView<>();
//        equipmentsTab.setContent(equipmentsTable);
//
//        // Tworzymy kolumny tabeli
//        TableColumn<Equipment, Integer> idColumn = new TableColumn<>("ID");
//        TableColumn<Equipment, String> nameColumn = new TableColumn<>("Name");
//        TableColumn<Equipment, String> statusColumn = new TableColumn<>("Status");
//        TableColumn<Equipment, String> zoneColumn = new TableColumn<>("Zone");
//
//        // Ustawiamy, jakie wartości mają być wyświetlane w kolumnach
//        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
//        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
//        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
//        zoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZone()));
//
//        // Dodajemy kolumny do tabeli
//        equipmentsTable.getColumns().addAll(idColumn, nameColumn, statusColumn, zoneColumn);
//
//        // Dodajemy dane do tabeli
//        equipmentsTable.getItems().addAll(equipment);
//
//        // Dodajemy zakładkę do TabPane
//        tabPane.getTabs().add(equipmentsTab);
//    }
//
//    private void addComponentsTab() {
//        Tab componentsTab = new Tab("Components");
//
//        // Tworzymy TableView dla komponentów
//        TableView<Component> componentsTable = new TableView<>();
//        componentsTab.setContent(componentsTable);
//
//        // Tworzymy kolumny tabeli
//        TableColumn<Component, Integer> idColumn = new TableColumn<>("ID");
//        TableColumn<Component, String> nameColumn = new TableColumn<>("Name");
//        TableColumn<Component, Integer> quantityColumn = new TableColumn<>("Quantity");
//
//        // Ustawiamy, jakie wartości mają być wyświetlane w kolumnach
//        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
//        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
//        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
//
//        // Dodajemy kolumny do tabeli
//        componentsTable.getColumns().addAll(idColumn, nameColumn, quantityColumn);
//
//        // Dodajemy dane do tabeli
//        componentsTable.getItems().addAll(components);
//
//        // Dodajemy zakładkę do TabPane
//        tabPane.getTabs().add(componentsTab);
//    }

    @FXML
    private void adminButtonAction() {
        tcpClientFX.logOut();
    }
}
