package server;

public class Equipment extends Inventory{
    private String status;
    private String zone;
    public Equipment(int id, String name, String status, String zone) {
        super(id, name);
        this.status = status;
        this.zone = zone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZone() {
        return zone;
    }

    @Override
    public void reserve(Task task) {

    }

    @Override
    public void cancelReservation(Task task) {

    }
}
