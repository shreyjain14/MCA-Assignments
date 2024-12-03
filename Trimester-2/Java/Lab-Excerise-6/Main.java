import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Repository<Product, String> productRepository = new GenericRepository<>();
        Repository<Customer, String> customerRepository = new GenericRepository<>();
        Repository<Order, String> orderRepository = new GenericRepository<>();

        int choice;

        do {
            System.out.println("\n--- E-Commerce Management System ---");
            System.out.println("1. Add Product");
            System.out.println("2. Add Customer");
            System.out.println("3. Add Order");
            System.out.println("4. View Products");
            System.out.println("5. View Customers");
            System.out.println("6. View Orders");
            System.out.println("7. Find Product by ID");
            System.out.println("8. Find Customer by ID");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1: 
                    System.out.print("Enter Product ID: ");
                    String productId = scanner.nextLine();
                    System.out.print("Enter Product Name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter Product Price: ");
                    double productPrice = scanner.nextDouble();
                    productRepository.add(new Product(productId, productName, productPrice));
                    System.out.println("Product added successfully!");
                    break;

                case 2: 
                    System.out.print("Enter Customer ID: ");
                    String customerId = scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    customerRepository.add(new Customer(customerId, customerName));
                    System.out.println("Customer added successfully!");
                    break;

                case 3: 
                    System.out.print("Enter Order ID: ");
                    String orderId = scanner.nextLine();
                    System.out.print("Enter Product ID for Order: ");
                    String orderProductId = scanner.nextLine();
                    System.out.print("Enter Customer ID for Order: ");
                    String orderCustomerId = scanner.nextLine();
                    orderRepository.add(new Order(orderId, orderProductId, orderCustomerId));
                    System.out.println("Order added successfully!");
                    break;

                case 4:
                    System.out.println("\nProducts:");
                    productRepository.findAll().forEach(System.out::println);
                    break;

                case 5: 
                    System.out.println("\nCustomers:");
                    customerRepository.findAll().forEach(System.out::println);
                    break;

                case 6: 
                    System.out.println("\nOrders:");
                    orderRepository.findAll().forEach(System.out::println);
                    break;

                case 7: 
                    System.out.print("Enter Product ID: ");
                    String searchProductId = scanner.nextLine();
                    Product product = productRepository.findById(searchProductId);
                    if (product != null) {
                        System.out.println("Product Found: " + product);
                    } else {
                        System.out.println("Product not found!");
                    }
                    break;

                case 8:
                    System.out.print("Enter Customer ID: ");
                    String searchCustomerId = scanner.nextLine();
                    Customer customer = customerRepository.findById(searchCustomerId);
                    if (customer != null) {
                        System.out.println("Customer Found: " + customer);
                    } else {
                        System.out.println("Customer not found!");
                    }
                    break;

                case 9: 
                    System.out.println("Exiting system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 9);

        scanner.close();
    }
}
