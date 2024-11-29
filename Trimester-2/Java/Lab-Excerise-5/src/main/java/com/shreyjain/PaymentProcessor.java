package com.shreyjain;

public class PaymentProcessor {
    public void processPayment(String threadName) {
        System.out.println(threadName + " is processing payment...");
        try {
            Thread.sleep(2000); // Simulate payment processing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadName + " completed payment.");
    }
}
