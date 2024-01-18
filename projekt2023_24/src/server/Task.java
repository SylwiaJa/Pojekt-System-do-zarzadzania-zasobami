package server;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

public class Task implements Serializable {
    private  String componentName;
    private  String equipmentName;
    private  String product;
    private  int quantityNOK;
    private int quantityOK;
    private int taskID;
    private String name;
    private String priority;
    private String description;
    private int norm;
    private List<Component> component;
    private Equipment equipment;
    private String status;
    private Time timeInStep;
    private Employee employee;
    private String zone;
    private int quantity;
    private int productID;
    private int orderID;
    private String category;


    public Task(int taskID, String name, String priority, String description, int norm, String zone, int quantity) {
        this.taskID = taskID;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.norm = norm;
        this.zone=zone;
        this.quantity=quantity;
    }
    public Task(int taskID, String name, String priority, String description, int norm, List<Component> component, Equipment equipment, String zone, int quantity, int productID, int orderID) {
        this.taskID = taskID;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.norm = norm;
        this.component = component;
        this.equipment=equipment;
        this.zone=zone;
        this.quantity=quantity;
        this.productID=productID;
        this.orderID=orderID;
    }

    public Task(int taskID, String name, String priority, String description, int norm, List<Component> component, Equipment equipment, String status, Time timeInStep, Employee employee) {
        this.taskID = taskID;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.norm = norm;
        this.component = component;
        this.equipment = equipment;
        this.status = status;
        this.timeInStep = timeInStep;
        this.employee = employee;
    }

    public Task(int taskID, String taskName, String priority, String description, String category, int norm, int quantityOK,
                int quantityNOK, String status,String product, int quantity, String zone, String equipment, String component) {
        this.taskID=taskID;
        this.name=taskName;
        this.priority=priority;
        this.description=description;
        this.category = category;
        this.norm=norm;
        this.quantityOK = quantityOK;
        this.quantityNOK = quantityNOK;
        this.product = product;
        this.quantity = quantity;
        this.zone =zone;
        this.equipmentName = equipment;
        this.componentName = component;
        this.status=status;


    }

    public Task(int taskID, String name, String priority, String description, String productName, int quantity, int norm, String status, String equipment, String component) {
        this.taskID=taskID;
        this.name=name;
        this.priority=priority;
        this.description=description;
        this.norm=norm;
        this.product = productName;
        this.quantity = quantity;
        this.equipmentName = equipment;
        this.componentName = component;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getProductID() {
        return productID;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public int getNorm() {
        return norm;
    }

    public String getZone() {
        return zone;
    }

    public void setQuantityNOK(int quantityNOK) {
        this.quantityNOK = quantityNOK;
    }

    public int getQuantityNOK() {
        return quantityNOK;
    }

    public int getQuantityOK() {
        return quantityOK;
    }

    public void setQuantityOK(int quantityOK) {
        this.quantityOK = quantityOK;
    }

    public List<Component> getComponent() {
        return component;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTaskID() {
        return taskID;
    }
    public void viewTaskDetails(){

    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getId() {
        return taskID;
    }
}
