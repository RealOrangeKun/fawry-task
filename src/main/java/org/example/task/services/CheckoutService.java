package org.example.task.services;

import org.example.task.interfaces.Expirable;
import org.example.task.models.Cart;
import org.example.task.models.Customer;

public class CheckoutService {

    public static void checkout(Customer customer, Cart cart) throws IllegalArgumentException {

        validateCartItems(cart);

        // Calculate the total cost of all items
        double totalCost = calculateTotalCost(cart);

        double shippingCost = ShippingService.calculateShippingCost(cart);

        double finalAmount = totalCost + shippingCost;

        checkSufficientCustomerBalance(customer, finalAmount);

        // First handle the shipping process
        ShippingService.ship(cart);

        // Deduct the quantity from inventory and add to total cost
        deductProductQuantities(cart);

        printCheckoutReceipt(cart, totalCost, shippingCost, finalAmount, customer);

        // Empty the cart after successful checkout
        cart.getProducts().clear();
    }

    private static void validateCartItems(Cart cart) throws IllegalArgumentException {
        for (var item : cart.getProducts()) {
            // Prevent checkout of expired products
            if (item.getProduct() instanceof Expirable expirable && expirable.isExpired()) {
                throw new IllegalArgumentException("Cannot checkout expired product: " + item.getProduct().getName());
            }

            // Verify sufficient inventory
            if (item.getQuantity() > item.getProduct().getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + item.getProduct().getName());
            }
        }
    }

    private static void printCheckoutReceipt(Cart cart, double totalCost, double shippingCost, double finalAmount ,Customer customer) {
        // Print receipt header
        System.out.println("** Checkout receipt **");
        // Print each item line
        for (var item : cart.getProducts()) {
            int qty = item.getQuantity();
            String name = item.getProduct().getName();
            double lineTotal = (item.getProduct().getPrice() * qty);
            System.out.printf("%-2dx %-15s %10.1f%n", qty, name, lineTotal);
        }

        // Print receipt totals
        System.out.println("-------------------------------");
        System.out.printf("%-18s %10.1f%n", "Subtotal", totalCost);
        System.out.printf("%-18s %10.1f%n", "Shipping", shippingCost);
        System.out.printf("%-18s %10.1f%n", "Amount", finalAmount);
        customer.setBalance(customer.getBalance() - finalAmount);
        System.out.printf("%-18s %10.1f%n", "New balance", customer.getBalance());
        System.out.println();

    }
    private static void checkSufficientCustomerBalance(Customer customer, double finalAmount) throws IllegalArgumentException {
        if (customer.getBalance() < finalAmount) {
            throw new IllegalArgumentException("Insufficient balance for checkout");
        }
    }
    private static void deductProductQuantities(Cart cart) {
        for (var item : cart.getProducts()) {
            item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getQuantity());
        }
    }
    private static double calculateTotalCost(Cart cart) {
        double totalCost = 0;
        for (var item : cart.getProducts()) {
            totalCost += item.getProduct().getPrice() * item.getQuantity();
        }
        return totalCost;
    }
}
