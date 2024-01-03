package server;

import java.io.Serializable;

public class Product implements Serializable {
    private  int id;
    private String name;
    private int quantityOrdered;
    private int quantityInProduction;
    private int quantityFinished;

    public Product( String name, int quantityOrdered) {
        this.name = name;
        this.quantityOrdered = quantityOrdered;
    }

    public Product( String name, int quantityOrdered, int quantityInProduction, int quantityFinished) {
        this.id = id;
        this.name = name;
        this.quantityOrdered = quantityOrdered;
        this.quantityInProduction = quantityInProduction;
        this.quantityFinished = quantityFinished;
    }
}
