

public abstract class Inventory {
    private String id;
    private String name;

    public Inventory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract void reserve(Task task);
    public abstract void cancelReservation(Task task);
}
