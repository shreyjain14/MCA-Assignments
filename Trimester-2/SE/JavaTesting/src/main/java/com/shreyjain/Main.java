package com.shreyjain;

import com.shreyjain.tax.TaxSlabs;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();

        System.out.print("Enter Income: ");
        long income = scanner.nextLong();

        TaxSlabs ts = new TaxSlabs();

        System.out.println(ts.getTaxSlab(age, income));

    }
}