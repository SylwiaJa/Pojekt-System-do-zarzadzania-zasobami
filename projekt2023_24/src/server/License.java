package server;

import java.io.Serializable;

public class License implements Serializable {
    private int id;
    private String name;
    private String description;

    public License(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean checkLicense(Employee employee, Task task){
        return true;
    }
}
