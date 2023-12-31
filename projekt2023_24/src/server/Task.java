package server;

import java.io.Serializable;
import java.sql.Time;

public class Task implements Serializable {
    private int taskID;
    private String name;
    private String priority;
    private String description;
    private int norm;
    private Component component;
    private Equipment equipment;
    private String status;
    private Time timeInStep;
    private Employee employee;
    private String zone;
    private int quantity;

    public Task(int taskID, String name, String priority, String description, int norm, String zone, int quantity) {
        this.taskID = taskID;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.norm = norm;
        this.zone=zone;
        this.quantity=quantity;
    }
    public Task(int taskID, String name, String priority, String description, int norm, Component component, Equipment equipment, String zone, int quantity) {
        this.taskID = taskID;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.norm = norm;
        this.component = component;
        this.equipment=equipment;
        this.zone=zone;
        this.quantity=quantity;
    }

    public Task(int taskID, String name, String priority, String description, int norm, Component component, Equipment equipment, String status, Time timeInStep, Employee employee) {
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
