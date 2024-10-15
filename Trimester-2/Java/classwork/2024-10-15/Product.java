class Product {
    
    private Long id;
    private String name;
    private Double price;

    public Product() {
        this.id = null;
        this.name = "";
        this.price = 0.0;
    }

    public Product(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Product(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.price = product.price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void display() {
        System.out.println("===================================");
        System.out.println("Product ID: " + id);
        System.out.println("Product Name: " + name);
        System.out.println("Product Price: " + price);
        System.out.println("===================================");
    }

    public void display(String message) {
        System.out.println("===================================");
        System.out.println("Message: " + message);
        System.out.println("Product ID: " + id);
        System.out.println("Product Name: " + name);
        System.out.println("Product Price: " + price);
        System.out.println("===================================");
    }

}
