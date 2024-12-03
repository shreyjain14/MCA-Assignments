package com.ecommerce.product.impl;

import com.ecommerce.product.OfflineProduct;

public class OfflineProductImpl implements OfflineProduct {

    protected Long id;
    protected String name;
    protected Integer stock = 0;

    public OfflineProductImpl(String name) {
        this.id = -1L; // get from db
        this.name = name;
    }

    public OfflineProductImpl(String name, Integer stock) {
        this.id = -1L; // get from db
        this.name = name;
        this.stock = stock;
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
        System.out.println("-----------------------------------------------");
    }

    @Override
    public int addStock(int stock) {
        this.stock += stock;
        return stock;
    }
}
