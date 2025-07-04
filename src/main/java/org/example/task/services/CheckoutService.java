package org.example.task.services;

import org.example.task.interfaces.Expirable;
import org.example.task.models.Cart;
import org.example.task.models.Customer;

public class CheckoutService {
    public static void checkout(Customer customer, Cart cart) throws IllegalArgumentException
    {
        // First handle the shipping process
        ShippingService.ship(cart);

        // Calculate the total cost of all items
        double totalCost = 0;
        for (var item : cart.getProducts()) {
            // Prevent checkout of expired products
            if(item.getProduct() instanceof Expirable expirable && expirable.isExpired()) {
                throw new IllegalArgumentException("Cannot checkout expired product: " + item.getProduct().getName());
            }

            // Verify sufficient inventory
            if(item.getQuantity() > item.getProduct().getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + item.getProduct().getName());
            }
            
            // Deduct the quantity from inventory and add to total cost
            item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getQuantity());

            totalCost += item.getProduct().getPrice() * item.getQuantity();
        }
        
        if (customer.getBalance() < totalCost) {
            throw new IllegalArgumentException("Insufficient balance for checkout");
        }

        // Print receipt header
        System.out.println("** Checkout receipt **");
        // Print each item line
        for (var item : cart.getProducts()) {
            int qty = item.getQuantity();
            String name = item.getProduct().getName();
            double lineTotal = (item.getProduct().getPrice() * qty);
            System.out.println(qty + "x " + name + " " + lineTotal);
        }

        // Print receipt totals
        System.out.println("----------------------");
        System.out.println("Subtotal " + totalCost);
        double shippingCost = ShippingService.calculateShippingCost(cart);
        System.out.println("Shipping " + shippingCost);
        System.out.println("Amount " + (totalCost + shippingCost));
        customer.setBalance(customer.getBalance() - totalCost);
        System.out.println("New balance " + customer.getBalance());
        System.out.println();
        // Empty the cart after successful checkout
        cart.getProducts().clear();
    }
}
