package server;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Employee{
    public Admin(int id, String name, String lastName, String role, String zone, String login, String password) {
        super(id, name, lastName, role, zone, login, password);
    }

    public Admin(int id, String name, String lastName, String role, String zone, String login, String password, Task task) {
        super(id, name, lastName, role, zone, login, password, task);
    }
    public void addRole(Employee employee, String role){

    }
    public void changeRole(Employee updateEmployee){
        MySQLDatabaseConnector mySQLDatabaseConnector = new MySQLDatabaseConnector();
        mySQLDatabaseConnector.updateEmployee(updateEmployee.getId(), updateEmployee.getRole(), updateEmployee.getZone());
    }



}
