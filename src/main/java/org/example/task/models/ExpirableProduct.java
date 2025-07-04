package org.example.task.models;

import java.util.Date;

import org.example.task.interfaces.Expirable;

public class ExpirableProduct extends Product implements Expirable {
    private Date expirationDate;

    public ExpirableProduct(String name, double price, int quantity, Date expirationDate) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean isExpired() {
        return expirationDate.before(new Date());
    }

    @Override
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

}
