import java.util.*;

class ECommerce {
    public static void main(String[] args) {
        // Using String concatenation and StringBuffer for product details
        String basePrice = "Rs. ";
        String priceAmount = "10000";
        String fullPrice = basePrice.concat(priceAmount); // String concatenation

        Electronics e1 = new Electronics(1L, "Laptop", fullPrice);
        e1.setBrand("Dell").setWarrenty("10 Years");
        e1.setDescription("High performance laptop", "8GB RAM", "512GB SSD");
        e1.printProduct();

        Electronics e2 = new Electronics(2L, "Laptop 2", fullPrice);
        e2.setBrand("HP").setWarrenty("10 Years");
        e2.setDescription("Budget laptop", "4GB RAM", "256GB SSD");
        e2.printProduct();

        List<Electronics> electronics = new ArrayList<>();
        electronics.add(e1);
        electronics.add(e2);

        e1.printProductList(electronics);

        // Demonstrate string operations
        System.out.println("\nProduct Search and Filtering:");
        e1.searchProducts(electronics, "Laptop");
        System.out.println("\nFormatted Product Summary:");
        e1.getFormattedSummary();
    }
}

abstract class Product {
    protected Long id;
    protected String name;
    protected String price;
    protected StringBuffer description; // Using StringBuffer for mutable description

    public Product(Long id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = new StringBuffer();
    }

    abstract String getCategory();

    abstract void printProduct();

    // Method to get product details as a formatted string
    public String getFormattedDetails() {
        return String.format("ID: %d, Name: %s, Price: %s", id, name, price);
    }
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

    // Method to set product description using StringBuffer
    public void setDescription(String... specs) {
        description.setLength(0); // Clear existing description
        description.append("Specifications:\n");
        for (String spec : specs) {
            description.append("- ").append(spec).append("\n");
        }
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
        System.out.println("Description:");
        System.out.println(description.toString());
        System.out.println("=====================================");
    }

    public void printProductList(List<Electronics> products) {
        for (Electronics product : products) {
            product.printProduct();
        }
    }

    // Method to search products using String operations
    public void searchProducts(List<Electronics> products, String keyword) {
        for (Electronics product : products) {
            if (product.name.toLowerCase().contains(keyword.toLowerCase())) {
                StringBuffer result = new StringBuffer()
                        .append("Found matching product: ")
                        .append(product.name)
                        .append(" (")
                        .append(product.brand)
                        .append(")");
                System.out.println(result.toString());
            }
        }
    }

    // Method to get formatted summary using StringBuffer
    public void getFormattedSummary() {
        StringBuffer summary = new StringBuffer();
        summary.append("Product Summary:\n")
                .append("----------------\n")
                .append("Name: ").append(name).append("\n")
                .append("Brand: ").append(brand).append("\n")
                .append("Price: ").append(price).append("\n")
                .append("Warranty: ").append(warrenty);

        System.out.println(summary.toString());
    }
}