package com.ecommerce.product;

public interface BaseProduct {

    Long id = null;
    String name = null;
    String description = null;

    public int stockAvailable();
    public int buy();

}
