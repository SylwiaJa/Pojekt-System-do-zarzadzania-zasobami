package client.controller;

import client.TCPClientFX;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.*;

import java.util.*;

public class ManagerController {
    private TCPClientFX tcpClientFX;
    private Employee employee;
    private List<Order> orders;
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

    public ManagerController(TCPClientFX tcpClientFX, Employee employee, List<Order> orders, List<Task> tasks, List<Employee> employees, List<Equipment> equipmentList) {
        this.tcpClientFX = tcpClientFX;
        this.employee = employee;
        this.orders = orders;
        this.tasks = tasks;
        this.employees = employees;
        this.equipmentList=equipmentList;
    }

    @FXML
    private void initialize() {
        name.setText("Name: " + employee.getName());
        lastName.setText("Last name: " + employee.getLastName());
        role.setText("Role: " + employee.getRole());
        zone.setText("Zone: " + employee.getZone());

        // Dodajemy zakładki i ich zawartość
        addOrdersTab();
        addTasksTab(tasks);
        addEmployeesTab(employees);
        addEquipmentsTab(equipmentList);

    }
    private void addOrdersTab() {
        Tab ordersTab = new Tab("Orders");

        // Tworzymy TableView dla zamówień
        TableView<Order> ordersTable = new TableView<>();
        ordersTab.setContent(ordersTable);

        // Tworzymy kolumny tabeli
        TableColumn<Order, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Order, String> productColumn = new TableColumn<>("Product");
        TableColumn<Order, Integer> quantityOrderedColumn = new TableColumn<>("Quantity Ordered");
        TableColumn<Order, Integer> quantityInProductionColumn = new TableColumn<>("Quantity In Production");
        TableColumn<Order, Integer> quantityFinishedColumn = new TableColumn<>("Quantity Finished");
        TableColumn<Order, String> statusColumn = new TableColumn<>("Status");
        TableColumn<Order, Void> startColumn = new TableColumn<>("Start");

        // Ustawiamy, jakie wartości mają być wyświetlane w kolumnach
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        productColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        quantityOrderedColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getQuantityOrdered()).asObject());
        quantityInProductionColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getQuantityInProduction()).asObject());
        quantityFinishedColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getQuantityFinished()).asObject());
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        // Ustawiamy przyciski w kolumnie "Start"
        startColumn.setCellFactory(col -> {
            TableCell<Order, Void> cell = new TableCell<>() {
                private final Button startButton = new Button("Start");

                {
                    startButton.setOnAction(event -> {
                        Order order = getTableView().getItems().get(getIndex());
                        int orderId = order.getId();
                        // Tutaj dodaj logikę do obsługi przycisku Start dla danego zamówienia (orderId)
                      List<Object> objects =  tcpClientFX.getOrderInfo(order);
                            List<Equipment> equipment = (List<Equipment>) objects.get(0);
                            List<Component> components = (List<Component>) objects.get(1);
                        // Otwórz nowe okno "New Task"
                        openNewTaskWindow(order,equipment,components, ordersTable);

                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || getTableRow().getItem() == null) {
                        setGraphic(null);
                    } else {
                        Order order = (Order) getTableRow().getItem();
                        String status = order.getStatus();

                        // Wyświetl przycisk tylko dla zamówień z statusami "accepted" lub "inprogress"
                        if ("accepted".equalsIgnoreCase(status) || "progress".equalsIgnoreCase(status)) {
                            setGraphic(startButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            };
            return cell;
        });

        // Dodajemy kolumny do tabeli
        ordersTable.getColumns().addAll(idColumn, productColumn, quantityOrderedColumn, quantityInProductionColumn, quantityFinishedColumn, statusColumn, startColumn);

        // Dodajemy wszystkie zamówienia do tabeli
        ordersTable.getItems().addAll(orders);

        // Dodajemy zakładkę do TabPane
        tabPane.getTabs().add(ordersTab);
    }

    private void openNewTaskWindow(Order selectedOrder, List<Equipment> equipmentList, List<Component> components, TableView<Order> ordersTable) {
        Stage newTaskStage = new Stage();
        newTaskStage.setTitle("New Task");

        // Tworzymy elementy interfejsu użytkownika
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();

        Label priorityLabel = new Label("Priority:");
        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("High", "Normal", "Low");

        Label descriptionLabel = new Label("Description:");
        TextArea descriptionTextArea = new TextArea();

        Label normLabel = new Label("Norm:");
        TextField normTextField = new TextField();

        Label componentLabel = new Label("Components:");
        VBox componentsVBox = new VBox();

        List<CheckBox> componentCheckboxes = new ArrayList<>();
        components.forEach(component -> componentCheckboxes.add(new CheckBox(component.getName())));
        componentsVBox.getChildren().addAll(componentCheckboxes);

        Label equipmentLabel = new Label("Equipment:");
        ComboBox<String> equipmentComboBox = new ComboBox<>();
        equipmentList.forEach(equipment -> equipmentComboBox.getItems().add(equipment.getName()));


        // Dodajemy pole Spinner dla ilości (Quantity)
        Label quantityLabel = new Label("Quantity:");
        Spinner<Integer> quantitySpinner = new Spinner<>(0, calculateMaxQuantity(selectedOrder), 0, 1);

        // Tworzymy przyciski "Apply" i "Cancel"
        Button applyButton = new Button("Apply");
        Button cancelButton = new Button("Cancel");

        // Ustawiamy akcję dla przycisku "Apply"
        applyButton.setOnAction(event -> {
            // Tutaj dodaj logikę do zastosowania wprowadzonych danych
            List<Component> selectedComponents = new ArrayList<>();

            for (CheckBox checkBox : componentCheckboxes) {
                if (checkBox.isSelected()) {
                    String componentName = checkBox.getText();

                    // Szukaj obiektu Component po nazwie w liście components
                    Optional<Component> foundComponent = components.stream()
                            .filter(component -> component.getName().equals(componentName))
                            .findFirst();

                    foundComponent.ifPresent(selectedComponents::add);
                }
            }

            // Pobierz nazwę wybranego sprzętu
            String selectedEquipmentName = equipmentComboBox.getValue();

            // Szukaj obiektu Equipment o danej nazwie w equipmentList
            Equipment selectedEquipment = equipmentList.stream()
                    .filter(equipment -> equipment.getName().equals(selectedEquipmentName))
                    .findFirst()
                    .orElse(null);

            assert selectedEquipment != null;
            Task task = new Task(1, nameTextField.getText(), priorityComboBox.getValue(), descriptionTextArea.getText(), Integer.parseInt(normTextField.getText()), selectedComponents, selectedEquipment, selectedEquipment.getZone(), quantitySpinner.getValue(),selectedOrder.getProduct().getId(), selectedOrder.getId());
         tcpClientFX.addTask(task);
            selectedOrder.getProduct().setQuantityInProduction(selectedOrder.getProduct().getQuantityInProduction()+quantitySpinner.getValue());
            selectedOrder.setStatus("progress");
            // Aktualizuj dane w tabeli
            ordersTable.getItems().clear(); // Wyczyść aktualne dane
            ordersTable.getItems().addAll(orders); // Dodaj nowe dane

            newTaskStage.close();
        });


        // Ustawiamy akcję dla przycisku "Cancel"
        cancelButton.setOnAction(event -> {
            // Tutaj dodaj logikę do anulowania wprowadzonych zmian
            newTaskStage.close();
        });

        // Ustawiamy layout za pomocą GridPane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Dodajemy etykiety i pola do wprowadzania danych do GridPane
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);

        gridPane.add(priorityLabel, 0, 1);
        gridPane.add(priorityComboBox, 1, 1);

        gridPane.add(descriptionLabel, 0, 2);
        gridPane.add(descriptionTextArea, 1, 2);

        gridPane.add(normLabel, 0, 3);
        gridPane.add(normTextField, 1, 3);

        gridPane.add(componentLabel, 0, 4);
        gridPane.add(componentsVBox, 1, 4);

        gridPane.add(equipmentLabel, 0, 5);
        gridPane.add(equipmentComboBox, 1, 5);


        gridPane.add(quantityLabel, 0, 7);
        gridPane.add(quantitySpinner, 1, 7);

        gridPane.add(new HBox(10, applyButton, cancelButton), 1, 8);

        // Ustawiamy scenę
        Scene scene = new Scene(gridPane, 400, 600); // Zwiększam wysokość okna
        newTaskStage.setScene(scene);

        // Pokazujemy nowe okno
        newTaskStage.show();
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


    private int calculateMaxQuantity(Order selectedOrder) {
        if (selectedOrder != null) {
            int maxQuantity = selectedOrder.getProduct().getQuantityOrdered()
                    - selectedOrder.getProduct().getQuantityInProduction()
                    - selectedOrder.getProduct().getQuantityFinished();

            return Math.max(maxQuantity, 0); // Nie pozwalamy na ujemne wartości
        }
        return 0;
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
    private void managerButtonAction() {
        tcpClientFX.logOut();
    }
}
