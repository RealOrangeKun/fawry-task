package org.example.task;

import java.util.Date;

import org.example.task.models.Cart;
import org.example.task.models.Customer;
import org.example.task.models.ExpirableShippableProduct;
import org.example.task.models.ShippableProduct;
import org.example.task.services.CheckoutService;


public class Main {

    public static void main(String[] args) {
        successTest();
        productExpiredFailureTest();
        insufficientBalanceFailureTest();
        insufficientStockFailureTest();
    }

    private static void successTest()
    {
        Customer customer = new Customer("John Doe", 100.0);
        Cart cart = new Cart(customer);
        Date expirationDate = new Date(System.currentTimeMillis() + 86400000); // 1 day
        
        ExpirableShippableProduct cheese = new ExpirableShippableProduct("Cheese", 20.5, 10, expirationDate, 0.5);
        ExpirableShippableProduct biscuits = new ExpirableShippableProduct("Biscuits", 10.7, 5, expirationDate, 0.2);
        ShippableProduct book = new ShippableProduct("Book", 15.1, 3, 0.3);
        
        cart.add(cheese, 2);
        cart.add(biscuits, 1);
        cart.add(book, 1);

        CheckoutService.checkout(customer, cart);
    }
    private static void productExpiredFailureTest()
    {
        Customer customer = new Customer("Jane Doe", 50.0);
        Cart cart = new Cart(customer);
        Date expirationDate = new Date(System.currentTimeMillis() - 86400000); // 1 day ago

        ExpirableShippableProduct cheese = new ExpirableShippableProduct("Cheese", 20.0, 10, expirationDate, 0.5);
        cart.add(cheese, 3); 
        cart.add(new ShippableProduct("Book", 15.0, 3, 0.3), 1);
        try {
            CheckoutService.checkout(customer, cart);
        } catch (IllegalArgumentException e) {
            System.out.println("Checkout failed: " + e.getMessage());
            System.out.println();
        }
    }
    private static void insufficientBalanceFailureTest()
    {
        Customer customer = new Customer("Alice", 10.0);
        Cart cart = new Cart(customer);
        Date expirationDate = new Date(System.currentTimeMillis() + 86400000); // 1 day
        ExpirableShippableProduct cheese = new ExpirableShippableProduct("Cheese", 20.0, 10, expirationDate, 0.5);
        cart.add(cheese, 1);
        try {
            CheckoutService.checkout(customer, cart);
        } catch (IllegalArgumentException e) {
            System.out.println("Checkout failed: " + e.getMessage());
            System.out.println();
        }
    }
    private static void insufficientStockFailureTest()
    {
        Customer customer = new Customer("Bob", 100.0);
        Cart cart = new Cart(customer);
        Date expirationDate = new Date(System.currentTimeMillis() + 86400000); // 1 day
        ExpirableShippableProduct cheese = new ExpirableShippableProduct("Cheese", 20.0, 2, expirationDate, 0.5);
        cart.add(cheese, 5); // Attempting to add more than available stock
        try {
            CheckoutService.checkout(customer, cart);
        } catch (IllegalArgumentException e) {
            System.out.println("Checkout failed: " + e.getMessage());
            System.out.println();
        }
    }
}
