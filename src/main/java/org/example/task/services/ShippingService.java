package org.example.task.services;

import org.example.task.interfaces.Shippable;
import org.example.task.models.Cart;

public class ShippingService {

    // Cost per kg for shipping
    private static final double SHIPPING_RATE = 10.0;

    // Calculate total shipping cost for all items in cart
    public static double calculateShippingCost(Cart cart) {
        // Sum up the cost for each shippable item based on weight
        return cart.getProducts().stream()
                .mapToDouble(item -> {
                    if (item.getProduct() instanceof Shippable shippable) {
                        return shippable.getWeight() * item.getQuantity() * SHIPPING_RATE;
                    }
                    return 0.0;
                })
                .sum();
    }

    public static void ship(Cart cart) {
        System.out.println("** Shipment notice **");

        double totalWeight = calculateTotalWeight(cart);

        printItemWeights(cart);

        System.out.printf("%-18s %8.1fkg%n", "Total package weight", totalWeight);
        System.out.println();
    }

    private static double calculateTotalWeight(Cart cart) {
        return cart.getProducts().stream()
                .filter(item -> item.getProduct() instanceof Shippable)
                .mapToDouble(item -> ((Shippable) item.getProduct()).getWeight() * item.getQuantity())
                .sum();
    }
    private static void printItemWeights(Cart cart)
    {
        for (var item : cart.getProducts()) {
            // Skip non-shippable products
            if (!(item.getProduct() instanceof Shippable shippable)) {
                continue;
            }
            int qty = item.getQuantity();
            double itemWeightKg = shippable.getWeight() * qty;
            int itemWeightGrams = (int) (itemWeightKg * 1000);
            System.out.printf("%-2dx %-17s %8dg%n", qty, shippable.getName(), itemWeightGrams);
        }
    }
}
