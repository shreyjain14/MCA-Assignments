import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;



public class Lambda {
    public static void main(String[] args) {
        // Consumer example: Process an order
        Consumer<Order> processOrder = order -> System.out.println("Processing order for: " + order.getProductName());
        Order order1 = new Order("Laptop", 1200);
        processOrder.accept(order1);

        // Function example: Calculate discount
        Function<Order, Double> calculateDiscount = order -> order.getPrice() * 0.10;
        double discount = calculateDiscount.apply(order1);
        System.out.println("Discount for " + order1.getProductName() + ": $" + discount);

        // Predicate example: Check if order is eligible for free shipping
        Predicate<Order> freeShipping = order -> order.getPrice() > 100;
        boolean isEligibleForFreeShipping = freeShipping.test(order1);
        System.out.println("Is " + order1.getProductName() + " eligible for free shipping? " + isEligibleForFreeShipping);

        // Supplier example: Generate a new order ID
        Supplier<String> orderIdSupplier = () -> "ORD" + (int)(Math.random() * 10000);
        String newOrderId = orderIdSupplier.get();
        System.out.println("Generated Order ID: " + newOrderId);
    }
}

class Order {
    private String productName;
    private double price;

    public Order(String productName, double price) {
        this.productName = productName;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }
}