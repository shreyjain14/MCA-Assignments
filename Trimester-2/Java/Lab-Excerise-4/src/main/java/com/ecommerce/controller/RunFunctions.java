package com.ecommerce.controller;

import com.ecommerce.product.impl.MixedProductImpl;
import com.ecommerce.product.impl.OfflineProductImpl;
import com.ecommerce.product.impl.OnlineProductImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RunFunctions {

    Menu menu = new Menu();
    Scanner scanner = new Scanner(System.in);
    int option;

    List<OfflineProductImpl> offlineProductsList = new ArrayList<>();
    List<OnlineProductImpl> onlineProductList = new ArrayList<>();
    List<MixedProductImpl> mixedProductsList = new ArrayList<>();

    public void runFunction(String operation) {
        switch (operation) {
            case "Add Product":
                menu.printMenu(menu.addProductMenuItems);
                option = scanner.nextInt();
                handleAddProduct(option);
                break;

            case "View All Products":
                viewAllProducts();
                break;

            case "Edit Product":
                menu.printMenu(menu.editProductMenuItems);
                option = scanner.nextInt();
                handleEditProduct(option);
                break;

            case "Buy a Product":
                buyProduct();
                break;

            default:
                System.out.println("Invalid Operation!");
                break;
        }
    }

    private void handleAddProduct(int option) {
        scanner.nextLine(); // consume newline
        switch (option) {
            case 1:
                System.out.print("Enter product name: ");
                String offlineName = scanner.nextLine();
                System.out.print("Enter stock: ");
                int offlineStock = scanner.nextInt();
                offlineProductsList.add(new OfflineProductImpl(offlineName, offlineStock));
                break;

            case 2:
                System.out.print("Enter product name: ");
                String onlineName = scanner.nextLine();
                System.out.print("Enter price: ");
                double onlinePrice = scanner.nextDouble();
                onlineProductList.add(new OnlineProductImpl(onlineName, onlinePrice));
                break;

            case 3:
                System.out.print("Enter product name: ");
                String mixedName = scanner.nextLine();
                System.out.print("Enter stock: ");
                int mixedStock = scanner.nextInt();
                System.out.print("Enter price: ");
                double mixedPrice = scanner.nextDouble();
                mixedProductsList.add(new MixedProductImpl(mixedName, mixedStock, mixedPrice));
                break;

            default:
                System.out.println("Invalid Choice!");
                break;
        }
    }

    private void viewAllProducts() {
        System.out.println("Offline Products:");
        for (OfflineProductImpl product : offlineProductsList) {
            product.display();
        }

        System.out.println("Online Products:");
        for (OnlineProductImpl product : onlineProductList) {
            product.display();
        }

        System.out.println("Mixed Products:");
        for (MixedProductImpl product : mixedProductsList) {
            product.display();
        }
    }

    private void handleEditProduct(int option) {
        // Implement edit product functionality based on the option
        // Similar to handleAddProduct, but for editing existing products
    }

    private void buyProduct() {
        // Implement buy product functionality
    }
}