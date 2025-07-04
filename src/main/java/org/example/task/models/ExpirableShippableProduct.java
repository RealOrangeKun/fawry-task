package org.example.task.models;

import java.util.Date;

import org.example.task.interfaces.Expirable;
import org.example.task.interfaces.Shippable;

public class ExpirableShippableProduct extends Product implements Shippable, Expirable {
    private Date expirationDate;
    private final double weight;

    public ExpirableShippableProduct(String name, double price, int quantity, Date expirationDate, double weight) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
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

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean isExpired() {
        return expirationDate.before(new Date());
    }
}
