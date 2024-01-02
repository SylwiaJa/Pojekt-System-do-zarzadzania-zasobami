package server;

import java.util.List;

public class ProductionEmployee extends Employee{
    public ProductionEmployee(int id, String name, String lastName, String role, String zone, String login, String password) {
        super(id, name, lastName, role, zone, login, password);
    }

    public ProductionEmployee(int id, String name, String lastName, String role, String zone, String login, String password, Task task) {
        super(id, name, lastName, role, zone, login, password, task);
    }
    @Override
    public List<Task> getListOfTasks(){
return null;
    }
    public Task getMyTask(){
return null;
    }
}
