package org.example.task.services;

import org.example.task.interfaces.Expirable;
import org.example.task.interfaces.Shippable;
import org.example.task.models.Cart;
import org.example.task.models.CartItem;

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
        double totalWeight = 0.0;
        for (CartItem item : cart.getProducts()) {
            // Skip non-shippable products
            if (!(item.getProduct() instanceof Shippable shippable)) {
                continue;
            }

            // Check if product is expired
            if (item.getProduct() instanceof Expirable expirable && expirable.isExpired()) {
                throw new IllegalArgumentException("Cannot ship expired product: " + item.getProduct().getName());
            }

            // Check inventory levels
            if (item.getQuantity() > item.getProduct().getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + item.getProduct().getName());
            }

            int qty = item.getQuantity();
            double itemWeightKg = shippable.getWeight() * qty;
            int itemWeightGrams = (int) (itemWeightKg * 1000);
            System.out.println(qty + "x " + shippable.getName() + " " + itemWeightGrams + "g");
            totalWeight += itemWeightKg;

        }
        System.out.println("Total package weight " + String.format("%.1fkg", totalWeight));
    }
}
