package client.controller;

import client.TCPClientFX;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
                        System.out.println("Start button clicked for order with ID: " + orderId);
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

        // Dodajemy kolumny do tabeli
        ordersTable.getColumns().addAll(idColumn, productColumn, quantityOrderedColumn, quantityInProductionColumn, quantityFinishedColumn, statusColumn, startColumn);

        // Dodajemy dane do tabeli
        ordersTable.getItems().addAll(orders);

        // Dodajemy zakładkę do TabPane
        tabPane.getTabs().add(ordersTab);
    }

    @FXML
    private void managerButtonAction() {
        tcpClientFX.logOut();
    }
}
