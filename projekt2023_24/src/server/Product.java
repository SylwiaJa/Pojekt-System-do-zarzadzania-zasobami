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

    public Product( int id, String name, int quantityOrdered, int quantityInProduction, int quantityFinished) {
        this.id = id;
        this.name = name;
        this.quantityOrdered = quantityOrdered;
        this.quantityInProduction = quantityInProduction;
        this.quantityFinished = quantityFinished;
    }

    public String getName() {
        return name;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public int getQuantityInProduction() {
        return quantityInProduction;
    }

    public int getQuantityFinished() {
        return quantityFinished;
    }

    @Override
    public String toString() {
        return  name  +
                "\t" + quantityOrdered +
                "\t" + quantityInProduction +
                "\t" + quantityFinished ;
    }

    public void setQuantityInProduction(int quantityInProduction) {
        this.quantityInProduction = quantityInProduction;
    }

    public int getId() {
        return id;
    }
}
