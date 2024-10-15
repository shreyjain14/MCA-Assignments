import java.util.List;

class Product {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Integer category;
    private String image;

    public Product() {
        this.id = null;
        this.name = "";
        this.description = "";
        this.price = 0.0;
        this.quantity = 0;
        this.category = 0;
        this.image = "";
    }

    public Product(Long id, String name, String description, Double price, Integer quantity, Integer category,
            String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.image = image;
    }

    public Product(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.description = product.description;
        this.price = product.price;
        this.quantity = product.quantity;
        this.category = product.category;
        this.image = product.image;
    }

    public void printProduct() {
        System.out.println("=====================================");
        System.out.println("Product ID: " + this.id);
        System.out.println("Product Name: " + this.name);
        System.out.println("Product Description: " + this.description);
        System.out.println("Product Price: " + this.price);
        System.out.println("Product Quantity: " + this.quantity);
        System.out.println("Product Category: " + this.category);
        System.out.println("Product Image: " + this.image);
        System.out.println("=====================================");
    }

    public void printProductList(List<Product> products) {
        for (Product product : products) {
            product.printProduct();
        }
    }

    public void setProduct(Long id, String name, String description, Double price, Integer quantity, Integer category,
            String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.image = image;
    }

}
