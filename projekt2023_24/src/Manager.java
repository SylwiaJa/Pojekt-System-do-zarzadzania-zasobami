public class Manager extends Leader{
    public Manager(String id, String name, String lastName, String role, String zone, String login, String password) {
        super(id, name, lastName, role, zone, login, password);
    }

    public Manager(String id, String name, String lastName, String role, String zone, String login, String password, Task task) {
        super(id, name, lastName, role, zone, login, password, task);
    }
    public void addTaskCategory(String category){

    }
    public void addNewTask(String name, String priority, String description, Integer norm, Component component ){

    }
    public void addTaskPriority(Task task, String priority){

    }
    public void changeTaskPriority(Task task, String  priority){

    }
    public void addLicenseToEmployee(Employee employee, License license){

    }
    public void addEquipment(Equipment equipment){

    }
    public void changeEquipmentStatus(Equipment equipment, String status){

    }
}