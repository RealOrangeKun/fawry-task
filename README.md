# Fawry Internship Task

Java implementation for a shopping cart system task with product expiration and shipping features.

## Overview

This task implements a simple e-commerce cart system with:

- Abstract product classes
- Expirable and shippable item types
- Cart management
- Checkout system with receipts
- Shipping cost calculation

## Task Structure

The task is organized with these main components:

- Interfaces: Define behavior for expirable and shippable products
- Models: Implement different product types and cart functionality
- Services: Handle checkout process and shipping calculations

## Output Examples

Shipping notice:
```
** Shipment notice **
2x Cheese 400g
1x Biscuits 700g
Total package weight 1.1kg
```

Checkout receipt:
```
** Checkout receipt **
2x Cheese 200
1x Biscuits 150
----------------------
Subtotal 350
Shipping 30
Amount 380
```

## Build and Run

Use Maven to build the task:
```
mvn clean compile
```

Run the main class:
```
java -cp target/classes org.example.task.Main
```