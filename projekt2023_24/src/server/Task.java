package server;

import java.sql.Time;

public class Task {
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

    public int getTaskID() {
        return taskID;
    }
    public void viewTaskDetails(){

    }
}
