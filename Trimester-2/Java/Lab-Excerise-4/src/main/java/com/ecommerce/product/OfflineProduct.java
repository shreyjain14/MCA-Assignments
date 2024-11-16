package com.ecommerce.product;

public interface OfflineProduct extends BaseProduct{

    public int stockAvailable();
    public int addStock(int stock);
    public int removeStock(int stock);

}
