package com.shreyjain;

public class Main {
    public static void main(String[] args) {
        System.out.println("E-Commerce Simulation Starting...");

        Inventory inventory = new Inventory();
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        // Creating threads
        Thread thread1 = new Thread(new OrderProcessor(inventory, paymentProcessor, "Thread-1"));
        Thread thread2 = new Thread(new OrderProcessor(inventory, paymentProcessor, "Thread-2"));
        Thread thread3 = new Thread(new OrderProcessor(inventory, paymentProcessor, "Thread-3"));
        Thread thread4 = new Thread(new OrderProcessor(inventory, paymentProcessor, "Thread-4"));
        Thread thread5 = new Thread(new OrderProcessor(inventory, paymentProcessor, "Thread-5"));

        // Demonstrating synchronized and non-synchronized methods
        System.out.println("\n--- Without Synchronization (Checking stock) ---");
        new Thread(() -> inventory.checkStock("Thread-1")).start();
        new Thread(() -> inventory.checkStock("Thread-2")).start();

        System.out.println("\n--- With Synchronization (Processing orders) ---");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nSimulation completed.");
    }
}
