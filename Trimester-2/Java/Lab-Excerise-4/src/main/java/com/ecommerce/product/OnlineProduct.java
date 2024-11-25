package com.ecommerce.product;

public interface OnlineProduct extends BaseProduct{

    public void update(String name, int price);
    public void increasePriceByPercentage(int percentage);
    public void decreasePriceByPercentage(int percentage);

}
