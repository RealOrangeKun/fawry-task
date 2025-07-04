package org.example.task.models;

import org.example.task.interfaces.Shippable;

public class ShippableProduct extends Product implements Shippable {
    private final double weight;

    public ShippableProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
