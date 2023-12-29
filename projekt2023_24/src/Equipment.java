public class Equipment extends Inventory{
    private String status;
    private String zone;
    public Equipment(String id, String name, String status, String zone) {
        super(id, name);
        this.status = status;
        this.zone = zone;
    }

    @Override
    public void reserve(Task task) {

    }

    @Override
    public void cancelReservation(Task task) {

    }
}
