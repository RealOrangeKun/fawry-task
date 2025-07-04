package org.example.task.interfaces;

// Interface for products that can be physically shipped
public interface Shippable {
    String getName();      // Get the product name
    double getWeight();    // Get weight in kg for shipping calculations
}
