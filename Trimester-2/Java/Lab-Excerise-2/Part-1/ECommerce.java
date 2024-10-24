class ECommerce {
    
    public static void main(String[] args) {

        OnlineProduct onlineProduct = new OnlineProduct();
        onlineProduct.productID = 1;
        onlineProduct.productName = "Product 1";
        onlineProduct.productPrice = 100.0;
        onlineProduct.productCategory = "Category 1";
        onlineProduct.productDescription = "Description 1";
        onlineProduct.link = "Link 1";
        onlineProduct.vaidity = 10;

        OfflineProduct offlineProduct = new OfflineProduct();
        offlineProduct.productID = 2;
        offlineProduct.productName = "Product 2";
        offlineProduct.productPrice = 200.0;
        offlineProduct.productCategory = "Category 2";
        offlineProduct.productDescription = "Description 2";
        offlineProduct.location = "Location 2";
        offlineProduct.quantity = 20;

        onlineProduct.display();
        offlineProduct.display();

    }

}

class Product {
    
    int productID;
    String productName;
    double productPrice;   
    String productCategory;
    String productDescription;

}

class OnlineProduct extends Product {

    String link;
    int vaidity;

    void display() {
        System.out.println("------------------------------");
        System.out.println("Product ID: " + productID);
        System.out.println("Product Name: " + productName);
        System.out.println("Product Price: " + productPrice);
        System.out.println("Product Category: " + productCategory);
        System.out.println("Product Description: " + productDescription);
        System.out.println("Link: " + link);
        System.out.println("Vaidity: " + vaidity);
        System.out.println("------------------------------");
    }

}

class OfflineProduct extends Product {

    String location;
    int quantity;

    void display() {
        System.out.println("------------------------------");
        System.out.println("Product ID: " + productID);
        System.out.println("Product Name: " + productName);
        System.out.println("Product Price: " + productPrice);
        System.out.println("Product Category: " + productCategory);
        System.out.println("Product Description: " + productDescription);
        System.out.println("Location: " + location);
        System.out.println("Quantity: " + quantity);
        System.out.println("------------------------------");
    }

}