package server;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

public class Task implements Serializable {
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

    public int getId() {
        return taskID;
    }
}
