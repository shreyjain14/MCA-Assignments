import java.util.*;

class ECommerceSystem {

    static class Product {
        String name;
        int quantity;
        String category;
        boolean sold;
        int id;

        Product(int id, String name, int quantity, String category) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.category = category;
            this.sold = false;
        }

        void sell() {
            this.sold = true;
        }

        @Override
        public String toString() {
            return "Product [ID=" + id + ", Name=" + name + ", Quantity=" + quantity + ", Category=" + category + ", Sold=" + sold + "]";
        }
    }

    private static Map<Integer, Product> productRegistry = new HashMap<>();
    private static Set<String> productNames = new HashSet<>();
    private static int idCounter = 1;

    private static String getValidStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            } else if (!input.matches("[a-zA-Z]+")) {
                System.out.println("Name should only contain alphabets. Please try again.");
            } else {
                return input;
            }
        }
    }

    private static int getValidIntegerInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        int input;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input <= 0) {
                    System.out.println("Quantity must be a positive integer greater than zero. Please enter a valid quantity.");
                } else {
                    return input;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }
    }

    private static void addProduct() {
        String name = getValidStringInput("Enter product name: ");
        while (productNames.contains(name)) {
            System.out.println("Product with this name already exists. Choose another name.");
            name = getValidStringInput("Enter product name: ");
        }
        int quantity = getValidIntegerInput("Enter product quantity: ");
        String category = getValidStringInput("Enter product category: ");

        Product newProduct = new Product(idCounter++, name, quantity, category);

        if (productNames.contains(newProduct.name)) {
            System.out.println("Product with the name " + newProduct.name + " already exists in the system.");
            return;
        }

        productRegistry.put(newProduct.id, newProduct);
        productNames.add(newProduct.name);

        System.out.println("Product added successfully: " + newProduct);
    }

    private static void sellProduct() {
        if (productRegistry.isEmpty()) {
            System.out.println("No products available for sale.");
            return;
        }

        int productId = getValidIntegerInput("Enter product ID to sell: ");
        if (productRegistry.containsKey(productId)) {
            Product product = productRegistry.get(productId);
            if (product.sold) {
                System.out.println("This product has already been sold.");
            } else {
                product.sell();
                System.out.println("Product sold successfully: " + product);
            }
        } else {
            System.out.println("Product ID not found.");
        }
    }

    private static void displayProducts() {
        if (productRegistry.isEmpty()) {
            System.out.println("No products available to display.");
        } else {
            System.out.println("All registered products:");
            for (Product product : productRegistry.values()) {
                System.out.println(product);
            }
        }
    }

    private static void viewSaleStatus() {
        if (productRegistry.isEmpty()) {
            System.out.println("No products available for sale.");
            return;
        }

        int productId = getValidIntegerInput("Enter product ID to view sale status: ");
        if (productRegistry.containsKey(productId)) {
            Product product = productRegistry.get(productId);
            System.out.println("Product sale status: " + (product.sold ? "Sold" : "Available"));
        } else {
            System.out.println("Product ID not found.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- E-Commerce System ---");
            System.out.println("1. Add Product");
            System.out.println("2. Sell Product");
            System.out.println("3. View All Products");
            System.out.println("4. View Sale Status");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = getValidIntegerInput("");

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    sellProduct();
                    break;
                case 3:
                    displayProducts();
                    break;
                case 4:
                    viewSaleStatus();
                    break;
                case 5:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
