package server;

import java.io.Serializable;

public abstract class Inventory implements Serializable {
    private int id;
    private String name;

    public Inventory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract void reserve(Task task);
    public abstract void cancelReservation(Task task);
}
