package com.ecommerce.product.impl;

import com.ecommerce.product.OfflineProduct;
import com.ecommerce.product.OnlineProduct;

public class MixedProductImpl implements OfflineProduct, OnlineProduct {

    protected Long id;
    protected String name;
    protected Integer stock;
    protected double price;

    public MixedProductImpl(String name, Integer stock, double price) {
        this.id = -1L; // get from db
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    @Override
    public int stockAvailable() {
        return stock;
    }

    @Override
    public boolean buy() {
        if (stock > 0) {
            stock -= 1;
            return true;
        } else {
            return false;
        }
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
    public int addStock(int stock) {
        this.stock += stock;
        return this.stock;
    }

    @Override
    public int removeStock(int stock) {
        this.stock -= stock;
        return this.stock;
    }

    @Override
    public void display() {
        System.out.println("-----------------------------------------------");
        System.out.println("Product ID: " + id);
        System.out.println("Product Name: " + name);
        System.out.println("Product Stock: " + stock);
        System.out.println("Product Price: " + price);
        System.out.println("-----------------------------------------------");
    }
}
