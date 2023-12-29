import java.util.List;

public abstract class Employee {
    private String id;
    private String name;
    private String lastName;
    private String role;
    private String zone;
    private String login;
    private String password;
    private Task task;

    public Employee(String id, String name, String lastName, String role, String zone, String login, String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.zone = zone;
        this.login = login;
        this.password = password;
    }

    public Employee(String id, String name, String lastName, String role, String zone, String login, String password, Task task) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.zone = zone;
        this.login = login;
        this.password = password;
        this.task = task;
    }
    public abstract List<Task> getListOfTasks();
    public void getTask(Task task){

    }
    public void acceptTask(Task task){

    }
    public void endTask(Task task){

    }
    public void addTaskResult(Task task, Result result){

    }
}
