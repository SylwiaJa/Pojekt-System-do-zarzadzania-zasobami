import java.util.List;

public class Leader extends Employee {
    public Leader(int id, String name, String lastName, String role, String zone, String login, String password) {
        super(id, name, lastName, role, zone, login, password);
    }

    public Leader(int id, String name, String lastName, String role, String zone, String login, String password, Task task) {
        super(id, name, lastName, role, zone, login, password, task);
    }

    @Override
    public List<Task> getListOfTasks() {
        return null;
    }

    public List<Employee> getListOfEmployeeAndTask() {
return  null;
    }

    public void getTimeReport() {

    }

    public void getZoneReport() {

    }
public void getEmployeeReport(){

}
public void getEquipmentStatusReport(){

}
public void getEquipmentTimeOfUseReport(){

}

}