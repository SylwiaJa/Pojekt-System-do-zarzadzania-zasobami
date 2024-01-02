package server;

import java.io.Serializable;
import java.util.List;

public class Employee implements Serializable {
    private int id;
    private String name;
    private String lastName;
    private String role;
    private String zone;
    private String login;
    private String password;
    private Task task;

    public Employee(int id, String name, String lastName, String role, String zone, String login, String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.zone = zone;
        this.login = login;
        this.password = password;
    }

    public Employee(int id, String name, String lastName, String role, String zone, String login, String password, Task task) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.zone = zone;
        this.login = login;
        this.password = password;
        this.task = task;
    }
    public  List<Task> getListOfTasks(){
        return null;
    };
    public void getTask(Task task){

    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void acceptTask(Task task){

    }
    public void endTask(Task task){

    }
    public void addTaskResult(Task task, Result result){

    }
}
