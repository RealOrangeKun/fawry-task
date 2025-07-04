package org.example.task.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final Customer customer;
    private final List<CartItem> products = new ArrayList<>();
    
    public Cart(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void add(Product item, int quantity) {
        products.add(new CartItem(item, quantity));
    }

    public void remove(CartItem item) {
        products.remove(item);
    }

    public List<CartItem> getProducts() {
        return products;
    }
    
}
