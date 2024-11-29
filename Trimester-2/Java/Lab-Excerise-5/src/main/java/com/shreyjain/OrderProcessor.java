package com.shreyjain;

public class OrderProcessor implements Runnable {
    private Inventory inventory;
    private PaymentProcessor paymentProcessor;
    private String threadName;

    public OrderProcessor(Inventory inventory, PaymentProcessor paymentProcessor, String threadName) {
        this.inventory = inventory;
        this.paymentProcessor = paymentProcessor;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        synchronized (inventory) {
            inventory.purchaseItem(threadName);
        }
        paymentProcessor.processPayment(threadName);
    }
}
