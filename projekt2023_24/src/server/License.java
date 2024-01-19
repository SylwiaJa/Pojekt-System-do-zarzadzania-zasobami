package server;

import java.io.Serializable;

public class License implements Serializable {
    private String id;
    private String name;
    private String description;

    public License(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
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
