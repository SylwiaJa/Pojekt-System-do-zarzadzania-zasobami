package client.controller;

import client.TCPClientFX;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.Employee;
import server.Equipment;
import server.Task;

import java.util.List;
import java.util.StringJoiner;


public class LeaderController {
private TCPClientFX tcpClientFX;
private Employee employee;
    private List<Task> tasks;
    private List<Employee> employees;
    private List<Equipment> equipmentList;
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

    public LeaderController(TCPClientFX tcpClientFX, Employee employee, List<Task> tasks, List<Employee> employees, List<Equipment> equipmentList) {
        this.tcpClientFX = tcpClientFX;
        this.employee = employee;
        this.tasks = tasks;
        this.employees = employees;
        this.equipmentList=equipmentList;
    }

    @FXML
    private void initialize() {
        name.setText("Name: "+employee.getName());
        lastName.setText("Last name: "+employee.getLastName());
        role.setText("Role: "+employee.getRole());
        zone.setText("Zone: "+employee.getZone());
        addTasksTab(tasks);
        addEmployeesTab(employees);
        addEquipmentsTab(equipmentList);
    }
    private void addTasksTab(List<Task> tasks) {
        Tab tasksTab = new Tab("Tasks");

        // Tworzymy TableView dla zadań
        TableView<Task> tasksTable = new TableView<>();
        tasksTab.setContent(tasksTable);

        // Tworzymy kolumny tabeli
        TableColumn<Task, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Task, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Task, String> priorityColumn = new TableColumn<>("Priority");
        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<Task, Integer> normColumn = new TableColumn<>("Norm");
        TableColumn<Task, String> zoneColumn = new TableColumn<>("Zone");
        TableColumn<Task, Integer> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<Task, Void> viewColumn = new TableColumn<>("View");

        // Ustawiamy, jakie wartości mają być wyświetlane w kolumnach
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priorityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPriority()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        normColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNorm()).asObject());
        zoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZone()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        // Ustawiamy przyciski w kolumnie "View"
        viewColumn.setCellFactory(col -> {
            TableCell<Task, Void> cell = new TableCell<>() {
                private final Button viewButton = new Button("View");

                {
                    viewButton.setOnAction(event -> {
                        Task task = getTableView().getItems().get(getIndex());
                        int taskId = task.getId();
                        // Tutaj dodaj logikę do obsługi przycisku View dla danego zadania (taskId)
                        System.out.println("View button clicked for task with ID: " + taskId);

                        openViewTaskWindow(task);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || getTableRow().getItem() == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(viewButton);
                    }
                }
            };
            return cell;
        });

        // Dodajemy kolumny do tabeli
        tasksTable.getColumns().addAll(idColumn, nameColumn, priorityColumn, descriptionColumn, normColumn, zoneColumn, quantityColumn, viewColumn);

        // Dodajemy wszystkie zadania do tabeli
        tasksTable.getItems().addAll(tasks);

        // Dodajemy zakładkę do TabPane
        tabPane.getTabs().add(tasksTab);
    }
    private void openViewTaskWindow(Task task) {
        List<String> taskInfo = tcpClientFX.getTaskInfo(task);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Task Details");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        for (String info : taskInfo) {
            Label label = new Label(info);
            layout.getChildren().add(label);
        }

        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> window.close());
        layout.getChildren().add(closeButton);

        Scene scene = new Scene(layout, 300, 400);
        window.setScene(scene);

        window.showAndWait();
    }
    private void addEmployeesTab(List<Employee> employees) {
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
        TableColumn<Employee, Void> viewColumn = new TableColumn<>("View");

        // Ustawiamy, jakie wartości mają być wyświetlane w kolumnach
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
        zoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZone()));

        // Ustawiamy przyciski w kolumnie "View"
        viewColumn.setCellFactory(col -> {
            TableCell<Employee, Void> cell = new TableCell<>() {
                private final Button viewButton = new Button("View");

                {
                    viewButton.setOnAction(event -> {
                        Employee employee = getTableView().getItems().get(getIndex());
                        int employeeId = employee.getId();
                        // Tutaj dodaj logikę do obsługi przycisku View dla danego pracownika (employeeId)
                        System.out.println("View button clicked for employee with ID: " + employeeId);

                        // Otwórz nowe okno "View Employee"
                        openViewEmployeeWindow(employee);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || getTableRow().getItem() == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(viewButton);
                    }
                }
            };
            return cell;
        });

        // Dodajemy kolumny do tabeli
        employeesTable.getColumns().addAll(idColumn, nameColumn, lastNameColumn, roleColumn, zoneColumn, viewColumn);

        // Dodajemy wszystkich pracowników do tabeli
        employeesTable.getItems().addAll(employees);

        // Dodajemy zakładkę do TabPane
        tabPane.getTabs().add(employeesTab);
    }

    private void openViewEmployeeWindow(Employee employee) {
        List<List<String>> empInfo =  tcpClientFX.getEmployeeInfo(employee);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Task Details");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));


        for (List<String> info : empInfo) {
            Label label = new Label(info.get(0)+" "+info.get(1)+" "+info.get(2)+" "+info.get(3)+" "+info.get(4));
            layout.getChildren().add(label);
        }

        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> window.close());
        layout.getChildren().add(closeButton);

        Scene scene = new Scene(layout, 300, 400);
        window.setScene(scene);

        window.showAndWait();

    }

    private void addEquipmentsTab(List<Equipment> equipments) {
        Tab equipmentsTab = new Tab("Equipments");

        // Tworzymy TableView dla sprzętu
        TableView<Equipment> equipmentsTable = new TableView<>();
        equipmentsTab.setContent(equipmentsTable);

        // Tworzymy kolumny tabeli
        TableColumn<Equipment, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Equipment, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Equipment, String> statusColumn = new TableColumn<>("Status");
        TableColumn<Equipment, String> zoneColumn = new TableColumn<>("Zone");
        TableColumn<Equipment, Void> viewColumn = new TableColumn<>("View");

        // Ustawiamy, jakie wartości mają być wyświetlane w kolumnach
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        zoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZone()));

        // Ustawiamy przyciski w kolumnie "View"
        viewColumn.setCellFactory(col -> {
            TableCell<Equipment, Void> cell = new TableCell<>() {
                private final Button viewButton = new Button("View");

                {
                    viewButton.setOnAction(event -> {
                        Equipment equipment = getTableView().getItems().get(getIndex());
                        List<List<String>> equipmentUse = tcpClientFX.getUseEquipment(equipment.getId());
                        openViewEquipmentWindow(equipment,equipmentUse);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || getTableRow().getItem() == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(viewButton);
                    }
                }
            };
            return cell;
        });

        // Dodajemy kolumny do tabeli
        equipmentsTable.getColumns().addAll(idColumn, nameColumn, statusColumn, zoneColumn, viewColumn);

        // Dodajemy wszystkie sprzęty do tabeli
        equipmentsTable.getItems().addAll(equipments);

        // Dodajemy zakładkę do TabPane
        tabPane.getTabs().add(equipmentsTab);
    }
    private void openViewEquipmentWindow(Equipment equipment, List<List<String>> equipmentUse) {
        // Tworzymy nowe okno "View Equipment"
        Stage viewEquipmentStage = new Stage();
        viewEquipmentStage.setTitle("View Equipment");

        // Tworzymy kontener VBox dla układu okna
        VBox vbox = new VBox(10);

        // Dodajemy etykiety i pola tekstowe do VBox
        Label nameLabel = new Label("Name: " + equipment.getName());

        // Lista rozwijana (ComboBox) dla statusu
        Label statusLable = new Label("Status: ");
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("available", "in use", "out of use");
        statusComboBox.setValue(equipment.getStatus()); // Ustawienie domyślnego statusu

        Label zoneLabel = new Label("Zone: " + equipment.getZone());

        // Dodajemy elementy do VBox
        vbox.getChildren().addAll(nameLabel, statusLable, statusComboBox, zoneLabel);

        // Dodaj informacje z equipmentUse
        for (List<String> innerList : equipmentUse) {
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (String value : innerList) {
                stringJoiner.add(value);

            }
            Label label = new Label(stringJoiner.toString());
            vbox.getChildren().add(label);

        }

        // Tworzymy HBox dla przycisków Apply i Cancel
        HBox buttonBox = new HBox(10);
        Button applyButton = new Button("Apply");
        Button cancelButton = new Button("Cancel");

        // Obsługa przycisku Apply
        applyButton.setOnAction(event -> {
            equipment.setStatus(statusComboBox.getValue());
            tcpClientFX.updateEquipment(equipment);

            // Zamknij okno po zastosowaniu zmian
            viewEquipmentStage.close();
        });

        // Obsługa przycisku Cancel
        cancelButton.setOnAction(event -> {
            // Zamknij okno bez zastosowywania zmian
            viewEquipmentStage.close();
        });

        // Dodaj przyciski do HBox
        buttonBox.getChildren().addAll(applyButton, cancelButton);

        // Dodaj HBox do VBox
        vbox.getChildren().add(buttonBox);

        // Ustawiamy VBox jako scenę
        Scene scene = new Scene(vbox, 400, 300);  // Zwiększyłem szerokość okna, aby pomieścić więcej informacji
        viewEquipmentStage.setScene(scene);

        // Pokazujemy nowe okno
        viewEquipmentStage.show();
    }
    @FXML
    private void leaderButtonAction() {
        tcpClientFX.logOut();
    }
}
