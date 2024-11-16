package com.ecommerce.controller;

import com.ecommerce.product.BaseProduct;
import com.ecommerce.product.OfflineProduct;
import com.ecommerce.product.impl.MixedProductImpl;
import com.ecommerce.product.impl.OfflineProductImpl;
import com.ecommerce.product.impl.OnlineProductImpl;

import java.util.List;
import java.util.Scanner;

public class RunFunctions {

    Menu menu = new Menu();
    Scanner scanner = new Scanner(System.in);
    int option;

    List<OfflineProductImpl> offlineProductsList;
    List<OnlineProductImpl> onlineProductList;
    List<MixedProductImpl> mixedProductsList;

    public void runFunction(String operation) {

        switch (operation) {

            case "Add Product":
                menu.printMenu(menu.addProductMenuItems);
                option = scanner.nextInt();
                break;

            case "View All Products":
                break;

            case "Edit Product":
                menu.printMenu(menu.editProductMenuItems);
                option = scanner.nextInt();
                break;

            case "Buy a Product":
                break;

            case "Add Offline Product":
                break;

            case "Add Online Product":
                break;

            case "Add Mixed Product":
                break;

            case "Update Product":
                break;

            case "Increase Price by Percentage":
                break;

            case "Decrease Price by Percentage":
                break;

            case "Add Stock":
                break;

            case "Remove Stock":
                break;

        }

    }
}