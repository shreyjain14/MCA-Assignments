package com.shreyjain;

public class Inventory {
    private int stock = 10;

    public synchronized void purchaseItem(String threadName) {
        if (stock > 0) {
            System.out.println(threadName + " is purchasing an item...");
            try {
                Thread.sleep(1000); // Simulate processing time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock--;
            System.out.println(threadName + " completed the purchase. Remaining stock: " + stock);
        } else {
            System.out.println("Stock is out. " + threadName + " cannot purchase.");
        }
    }

    public void checkStock(String threadName) {
        System.out.println(threadName + " is checking stock: " + stock);
    }
}
