import java.util.List;

class ECommerce {
    
    public static void main(String[] args) {

        OnlineProduct onlineProduct = new OnlineProduct();
        onlineProduct.setProductID(1);
        onlineProduct.productName = "Minecraft Game Code";
        onlineProduct.productPrice = 100.0;
        onlineProduct.productCategory = "Gameing";
        onlineProduct.productDescription = "Sandbox Game";
        onlineProduct.link = "https://www.minecraft.net/en-us/";
        onlineProduct.vaidity = 10;

        OfflineProduct offlineProduct = new OfflineProduct();
        offlineProduct.setProductID(2);
        offlineProduct.productName = "Rubik's Cube";
        offlineProduct.productPrice = 200.0;
        offlineProduct.productCategory = "Puzzle";
        offlineProduct.productDescription = "3D Combination Puzzle";
        offlineProduct.location = "Banglore";
        offlineProduct.quantity = 20;

        onlineProduct.display();
        onlineProduct.display(List.of(onlineProduct));

        offlineProduct.display();
        offlineProduct.display(List.of(offlineProduct));

    }

}

class Product {
    
    private int productID;
    String productName;
    double productPrice;   
    String productCategory;
    String productDescription;

    void display() {}
    void display(List<Product> products) {};

    void setProductID(int productID) {
        this.productID = productID;
    }

    int getProductID() {
        return productID;
    }

}

class OnlineProduct extends Product {

    String link;
    int vaidity;

    @Override
    void display() {
        System.out.println("------------------------------");
        System.out.println("Product ID: " + getProductID());
        System.out.println("Product Name: " + productName);
        System.out.println("Product Price: " + productPrice);
        System.out.println("Product Category: " + productCategory);
        System.out.println("Product Description: " + productDescription);
        System.out.println("Link: " + link);
        System.out.println("Vaidity: " + vaidity);
        System.out.println("------------------------------");
    }

    @Override
    void display(List<Product> products) {
        for (Product product : products) {
            product.display();
        }
    }

}

class OfflineProduct extends Product {

    String location;
    int quantity;

    @Override
    void display() {
        System.out.println("------------------------------");
        System.out.println("Product ID: " + getProductID());
        System.out.println("Product Name: " + productName);
        System.out.println("Product Price: " + productPrice);
        System.out.println("Product Category: " + productCategory);
        System.out.println("Product Description: " + productDescription);
        System.out.println("Location: " + location);
        System.out.println("Quantity: " + quantity);
        System.out.println("------------------------------");
    }

    @Override
    void display(List<Product> products) {
        for (Product product : products) {
            product.display();
        }
    }

}