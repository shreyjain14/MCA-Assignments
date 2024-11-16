package com.ecommerce;

import com.ecommerce.controller.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        Scanner scanner = new Scanner(System.in);

        while (true) {

            menu.printMenu(menu.mainMenuItems);
            int option = scanner.nextInt();

            int response = menu.useMenu(menu.mainMenuItems, option);
            if (response == 1) break;

        }

    }
}