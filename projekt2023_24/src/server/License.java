package server;

public class License {
    private String id;
    private String name;
    private String description;

    public License(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public boolean checkLicense(Employee employee, Task task){
        return true;
    }
}
