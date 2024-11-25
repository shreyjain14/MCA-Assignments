package com.ecommerce.product.impl;

import com.ecommerce.product.OnlineProduct;

public class OnlineProductImpl implements OnlineProduct {

    protected Long id;
    protected String name;
    protected double price;

    public OnlineProductImpl(String name, double price) {
        this.id = -1L; // get from db
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean buy() {
        return true;
    }

    @Override
    public void update(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public void increasePriceByPercentage(int percentage) {
        this.price += this.price*percentage/100;
    }

    @Override
    public void decreasePriceByPercentage(int percentage) {
        this.price -= this.price*percentage/100;
    }

    @Override
    public void display() {
        System.out.println("-----------------------------------------------");
        System.out.println("Product ID: " + id);
        System.out.println("Product Name: " + name);
        System.out.println("Product Price: " + price);
        System.out.println("-----------------------------------------------");
    }
}
