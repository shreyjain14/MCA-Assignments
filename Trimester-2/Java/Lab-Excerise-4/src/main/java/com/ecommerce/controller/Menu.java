package com.ecommerce.controller;

import java.util.List;

public class Menu {

    final public List<String> mainMenuItems = List.of(
            "Add Product",
            "View All Products",
            "Edit Product",
            "Buy a Product"
    );

    final public List<String> addProductMenuItems = List.of(
            "Add Offline Product",
            "Add Online Product",
            "Add Mixed Product"
    );

    final public List<String> editProductMenuItems = List.of(
            "Update Product",
            "Increase Price by Percentage",
            "Decrease Price by Percentage",
            "Add Stock",
            "Remove Stock"
    );

    public Menu() {}

    public void printMenu(List<String> menuList) {
        int idx = 1;
        System.out.println("-----------------------------------------------");
        for (String menuItem : menuList) {
            System.out.println(idx + ". " + menuItem);
            idx ++;
        };
        System.out.println(idx + ". Exit");
        System.out.println("-----------------------------------------------");
        System.out.print("Enter Option: ");
    }

    public int useMenu(List<String> menuList, int option) {
        int menuLength = menuList.size();
        if (option == menuLength + 1) {
            return 1;
        }
        else if (1 > option || option > menuLength) {
            System.out.println("Invalid Choice!");
            return 0;
        }
        else {
            // TODO RUN OPERATION
            return 0;
        }
    }

};
