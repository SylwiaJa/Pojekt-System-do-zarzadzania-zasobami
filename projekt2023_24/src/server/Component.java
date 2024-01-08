package server;

public class Component extends Inventory{
    public  int quantity;
    public Component(int id, String name, int quantity) {
        super(id, name);
        this.quantity = quantity;
    }

    @Override
    public void reserve(Task task) {

    }

    @Override
    public void cancelReservation(Task task) {

    }
    public void updateQuantity(int quantity){

    }
}
