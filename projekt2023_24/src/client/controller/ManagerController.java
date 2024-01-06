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
import javafx.stage.Stage;
import server.Employee;
import server.Order;

import java.util.List;
import java.util.stream.Collectors;

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

    @FXML
    private TabPane tabPane;

    public ManagerController(TCPClientFX tcpClientFX, Employee employee, List<Order> orders) {
        this.tcpClientFX = tcpClientFX;
        this.employee = employee;
        this.orders = orders;
    }

    @FXML
    private void initialize() {
        name.setText("Name: " + employee.getName());
        lastName.setText("Last name: " + employee.getLastName());
        role.setText("Role: " + employee.getRole());
        zone.setText("Zone: " + employee.getZone());

        // Dodajemy zakładki i ich zawartość
        addOrdersTab();
        addViewOrdersTab();
    }
    private void addViewOrdersTab() {
        Tab viewOrdersTab = new Tab("View Orders");

        // Tworzymy TableView dla wszystkich zamówień
        TableView<Order> viewOrdersTable = new TableView<>();
        viewOrdersTab.setContent(viewOrdersTable);

        // Tworzymy kolumny tabeli
        TableColumn<Order, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Order, String> productColumn = new TableColumn<>("Product");
        TableColumn<Order, Integer> quantityOrderedColumn = new TableColumn<>("Quantity Ordered");
        TableColumn<Order, Integer> quantityInProductionColumn = new TableColumn<>("Quantity In Production");
        TableColumn<Order, Integer> quantityFinishedColumn = new TableColumn<>("Quantity Finished");
        TableColumn<Order, String> statusColumn = new TableColumn<>("Status");

        // Ustawiamy, jakie wartości mają być wyświetlane w kolumnach
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        productColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        quantityOrderedColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getQuantityOrdered()).asObject());
        quantityInProductionColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getQuantityInProduction()).asObject());
        quantityFinishedColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getQuantityFinished()).asObject());
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        // Dodajemy kolumny do tabeli
        viewOrdersTable.getColumns().addAll(idColumn, productColumn, quantityOrderedColumn, quantityInProductionColumn, quantityFinishedColumn, statusColumn);

        // Dodajemy wszystkie zamówienia do tabeli
        viewOrdersTable.getItems().addAll(orders);

        // Dodajemy zakładkę do TabPane
        tabPane.getTabs().add(viewOrdersTab);
    }

    private void addOrdersTab() {
        Tab ordersTab = new Tab("Orders accepted");

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
                        System.out.println("Start button clicked for order with ID: " + orderId);

                        // Otwórz nowe okno "New Task"
                        openNewTaskWindow(order);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : startButton);
                }
            };
            return cell;
        });

        // Filtrujemy zamówienia o statusie "accepted"
        List<Order> acceptedOrders = orders.stream()
                .filter(order -> "accepted".equalsIgnoreCase(order.getStatus()))
                .collect(Collectors.toList());

        // Dodajemy kolumny do tabeli
        ordersTable.getColumns().addAll(idColumn, productColumn, quantityOrderedColumn, quantityInProductionColumn, quantityFinishedColumn, statusColumn, startColumn);

        // Dodajemy tylko zamówienia o statusie "accepted"
        ordersTable.getItems().addAll(acceptedOrders);

        // Dodajemy zakładkę do TabPane
        tabPane.getTabs().add(ordersTab);

    }

    private void openNewTaskWindow(Order selectedOrder) {
        Stage newTaskStage = new Stage();
        newTaskStage.setTitle("New Task");

        // Tworzymy elementy interfejsu użytkownika
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();

        Label priorityLabel = new Label("Priority:");
        TextField priorityTextField = new TextField();

        Label descriptionLabel = new Label("Description:");
        TextArea descriptionTextArea = new TextArea();

        Label normLabel = new Label("Norm:");
        TextField normTextField = new TextField();

        Label componentLabel = new Label("Component:");
        ComboBox<String> componentComboBox = new ComboBox<>();
        componentComboBox.getItems().addAll("Component 1", "Component 2", "Component 3", "Component 4", "Component 5");

        Label equipmentLabel = new Label("Equipment:");
        ComboBox<String> equipmentComboBox = new ComboBox<>();
        equipmentComboBox.getItems().addAll("Equipment 1", "Equipment 2", "Equipment 3", "Equipment 4", "Equipment 5");

        Label zoneLabel = new Label("Zone:");
        ComboBox<String> zoneComboBox = new ComboBox<>();
        zoneComboBox.getItems().addAll("Zone 1", "Zone 2", "Zone 3", "Zone 4", "Zone 5");

        // Dodajemy pole Spinner dla ilości (Quantity)
        Label quantityLabel = new Label("Quantity:");
        Spinner<Integer> quantitySpinner = new Spinner<>(0, calculateMaxQuantity(selectedOrder), 0, 1);

        // Tworzymy przyciski "Apply" i "Cancel"
        Button applyButton = new Button("Apply");
        Button cancelButton = new Button("Cancel");

        // Ustawiamy akcję dla przycisku "Apply"
        applyButton.setOnAction(event -> {
            // Tutaj dodaj logikę do zastosowania wprowadzonych danych
            System.out.println("Applying changes...");
            newTaskStage.close();
        });

        // Ustawiamy akcję dla przycisku "Cancel"
        cancelButton.setOnAction(event -> {
            // Tutaj dodaj logikę do anulowania wprowadzonych zmian
            System.out.println("Cancelling changes...");
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
        gridPane.add(priorityTextField, 1, 1);

        gridPane.add(descriptionLabel, 0, 2);
        gridPane.add(descriptionTextArea, 1, 2);

        gridPane.add(normLabel, 0, 3);
        gridPane.add(normTextField, 1, 3);

        gridPane.add(componentLabel, 0, 4);
        gridPane.add(componentComboBox, 1, 4);

        gridPane.add(equipmentLabel, 0, 5);
        gridPane.add(equipmentComboBox, 1, 5);

        gridPane.add(zoneLabel, 0, 6);
        gridPane.add(zoneComboBox, 1, 6);

        gridPane.add(quantityLabel, 0, 7);
        gridPane.add(quantitySpinner, 1, 7);

        gridPane.add(new HBox(10, applyButton, cancelButton), 1, 8);

        // Ustawiamy scenę
        Scene scene = new Scene(gridPane, 400, 600); // Zwiększam wysokość okna
        newTaskStage.setScene(scene);

        // Pokazujemy nowe okno
        newTaskStage.show();
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



    @FXML
    private void managerButtonAction() {
        tcpClientFX.logOut();
    }
}
