package com.ecommerce;

import com.ecommerce.product.impl.MixedProductImpl;
import com.ecommerce.product.impl.OfflineProductImpl;
import com.ecommerce.product.impl.OnlineProductImpl;

public class Main2 {

    public static void main(String[] args) {

        OfflineProductImpl offlineProduct = new OfflineProductImpl("Chess", 10);

        System.out.println(offlineProduct.stockAvailable());
        System.out.println(offlineProduct.addStock(10));
        System.out.println(offlineProduct.removeStock(10));
        System.out.println(offlineProduct.buy());
        offlineProduct.display();

        OnlineProductImpl onlineProduct = new OnlineProductImpl("Java Notes", 10.99);

        System.out.println(onlineProduct.buy());
        onlineProduct.decreasePriceByPercentage(10);
        onlineProduct.display();
        onlineProduct.increasePriceByPercentage(10);
        onlineProduct.display();
        onlineProduct.update("Java Notes 2024", 10);

        MixedProductImpl mixedProduct = new MixedProductImpl("Lego Star Wars", 10, 19.99);
        mixedProduct.addStock(10);
        mixedProduct.display();
        mixedProduct.buy();
        mixedProduct.decreasePriceByPercentage(10);
        mixedProduct.increasePriceByPercentage(10);
        mixedProduct.removeStock(3);
        mixedProduct.addStock(5);
        mixedProduct.update("Lego Star Wars 24", 15);
        mixedProduct.display();


    }

}
