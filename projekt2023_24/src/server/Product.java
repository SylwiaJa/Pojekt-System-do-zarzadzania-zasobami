package server;

public class Product {
    private  int id;
    private String name;
    private int quantityOrdered;
    private int quantityInProduction;
    private int quantityFinished;

    public Product(int id, String name, int quantityOrdered) {
        this.id = id;
        this.name = name;
        this.quantityOrdered = quantityOrdered;
    }

    public Product(int id, String name, int quantityOrdered, int quantityInProduction, int quantityFinished) {
        this.id = id;
        this.name = name;
        this.quantityOrdered = quantityOrdered;
        this.quantityInProduction = quantityInProduction;
        this.quantityFinished = quantityFinished;
    }
}
