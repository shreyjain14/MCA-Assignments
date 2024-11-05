import java.util.*;

class ECommerce {

    public static void main(String[] args) {

        Electronics e1 = new Electronics(1L, "Laptop", "Rs. 10000");
        e1.setBrand("Dell").setWarrenty("10 Years");
        e1.printProduct();

        Electronics e2 = new Electronics(2L, "Laptop 2", "Rs. 10000");
        e2.setBrand("HP").setWarrenty("10 Years");
        e2.printProduct();

        List<Electronics> electronics = new ArrayList<>();
        electronics.add(e1);
        electronics.add(e2);

        e1.printProductList(electronics);

    }

}

abstract class Product {

    protected Long id;
    protected String name;
    protected String price;

    public Product(Long id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    abstract String getCategory();

    abstract void printProduct();

}

class Electronics extends Product {

    private String brand;
    private String warrenty;

    public Electronics(Long id, String name, String price) {
        super(id, name, price);
    }

    public Electronics setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public Electronics setWarrenty(String warrenty) {
        this.warrenty = warrenty;
        return this;
    }

    @Override
    String getCategory() {
        return "Electronics";
    }

    @Override
    public void printProduct() {
        System.out.println("=====================================");
        System.out.println("Electronics ID: " + this.id);
        System.out.println("Electronics Name: " + this.name);
        System.out.println("Electronics Price: " + this.price);
        System.out.println("Electronics Brand: " + this.brand);
        System.out.println("Electronics Warrenty: " + this.warrenty);
        System.out.println("=====================================");
    }

    public void printProductList(List<Electronics> products) {
        for (Electronics product : products) {
            product.printProduct();
        }
    }

}