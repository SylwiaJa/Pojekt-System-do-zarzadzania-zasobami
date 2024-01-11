package server;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private String status;
    private Product product;

    public Order(int id, String status, Product product) {
        this.id = id;
        this.status = status;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public int getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
