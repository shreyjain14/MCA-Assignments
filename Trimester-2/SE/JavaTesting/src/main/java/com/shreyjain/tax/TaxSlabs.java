package com.shreyjain.tax;

public class TaxSlabs {

    public int getTaxSlab(int age, long income) {

        if (income <= 0) {
            return -1;
        } if (age < 18) {
            return -1;
        }
        
        if (age < 60) {
            if (income <= 250000) {
                return 0;
            } else if (250001 <= income && income <= 500000) {
                return 5;
            } else if (500001 <= income && income <= 750000) {
                return 10;
            } else if (750001 <= income && income <= 1000000) {
                return 15;
            } else if (1000001 <= income && income <= 1250000) {
                return 20;
            } else if (1250001 <= income && income <= 1500000) {
                return 25;
            } else {
                return 30;
            }
        } else if (60 <= age && age < 80) {
            if (income <= 300000) {
                return 0;
            } else if (300001 <= income && income <= 500000) {
                return 5;
            } else if (500001 <= income && income <= 750000) {
                return 10;
            } else if (750001 <= income && income <= 1000000) {
                return 15;
            } else if (1000001 <= income && income <= 1250000) {
                return 20;
            } else if (1250001 <= income && income <= 1500000) {
                return 25;
            } else {
                return 30;
            }
        } else {
            if (income <= 500000) {
                return 0;
            } else if (500001 <= income && income <= 1000000) {
                return 20;
            } else {
                return 30;
            }
        }

    }

}
