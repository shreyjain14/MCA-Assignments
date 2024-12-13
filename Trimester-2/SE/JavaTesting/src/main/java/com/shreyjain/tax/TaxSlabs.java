package com.shreyjain.tax;

public class TaxSlabs {

    public long getTaxSlab(int age, long income) {

        if (income <= 0) {
            return -1;
        }
        if (age < 18 || age > 122) {
            return -1;
        }

        long tax = 0;

        if (age < 60) {
            tax += calculateTax(income, 0, 250000, 0);
            tax += calculateTax(income, 250000, 500000, 5);
            tax += calculateTax(income, 500000, 750000, 10);
            tax += calculateTax(income, 750000, 1000000, 15);
            tax += calculateTax(income, 1000000, 1250000, 20);
            tax += calculateTax(income, 1250000, 1500000, 25);
            tax += calculateTax(income, 1500000, Long.MAX_VALUE, 30);
        } else if (age < 80) {
            tax += calculateTax(income, 0, 300000, 0);
            tax += calculateTax(income, 300000, 500000, 5);
            tax += calculateTax(income, 500000, 750000, 10);
            tax += calculateTax(income, 750000, 1000000, 15);
            tax += calculateTax(income, 1000000, 1250000, 20);
            tax += calculateTax(income, 1250000, 1500000, 25);
            tax += calculateTax(income, 1500000, Long.MAX_VALUE, 30);
        } else {
            tax += calculateTax(income, 0, 500000, 0);
            tax += calculateTax(income, 500000, 1000000, 20);
            tax += calculateTax(income, 1000000, Long.MAX_VALUE, 30);
        }

        return tax;
    }

    private long calculateTax(long income, long lowerLimit, long upperLimit, int rate) {
        if (income > lowerLimit) {
            long taxableIncome = Math.min(income, upperLimit) - lowerLimit;
            return taxableIncome * rate / 100;
        }
        return 0;
    }
}
