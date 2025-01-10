import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EcommerceApplication {

    private static List<Product> products = new ArrayList<>();
    private static DefaultListModel<String> productListModel = new DefaultListModel<>();
    private static JTextArea productDetails = new JTextArea(5, 20);
    private static JComboBox<String> categoryCombo;
    private static DefaultListModel<String> cartListModel = new DefaultListModel<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EcommerceApplication::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        initializeProducts();

        JFrame frame = new JFrame("E-commerce Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel browsePanel = new JPanel(new BorderLayout());

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Category:"));
        categoryCombo = new JComboBox<>(new String[]{"All", "Electronics", "Clothing", "Home Appliances"});
        categoryCombo.addActionListener(e -> filterProducts());
        filterPanel.add(categoryCombo);
        browsePanel.add(filterPanel, BorderLayout.NORTH);

        JList<String> productList = new JList<>(productListModel);
        productList.addListSelectionListener(e -> showProductDetails(productList.getSelectedValue()));
        JScrollPane productScrollPane = new JScrollPane(productList);
        browsePanel.add(productScrollPane, BorderLayout.CENTER);

        JPanel detailsPanel = new JPanel(new BorderLayout());
        productDetails.setEditable(false);
        detailsPanel.add(new JScrollPane(productDetails), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> addToCart(productList.getSelectedValue()));
        buttonPanel.add(addToCartButton);
        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        browsePanel.add(detailsPanel, BorderLayout.SOUTH);

        JPanel cartPanel = new JPanel(new BorderLayout());
        JList<String> cartList = new JList<>(cartListModel);
        JScrollPane cartScrollPane = new JScrollPane(cartList);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Browse Products", browsePanel);
        tabbedPane.addTab("Cart", cartPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);

        filterProducts();
    }

    private static void initializeProducts() {
        products.add(new Product("Laptop", "Electronics", "$1200"));
        products.add(new Product("Smartphone", "Electronics", "$800"));
        products.add(new Product("T-Shirt", "Clothing", "$20"));
        products.add(new Product("Jeans", "Clothing", "$40"));
        products.add(new Product("Blender", "Home Appliances", "$50"));
    }

    private static void filterProducts() {
        String selectedCategory = (String) categoryCombo.getSelectedItem();
        List<Product> filteredProducts = products;

        if (!"All".equals(selectedCategory)) {
            filteredProducts = products.stream()
                    .filter(p -> p.getCategory().equals(selectedCategory))
                    .collect(Collectors.toList());
        }

        productListModel.clear();
        for (Product product : filteredProducts) {
            productListModel.addElement(product.getName());
        }
    }

    private static void showProductDetails(String productName) {
        Product product = products.stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst()
                .orElse(null);

        if (product != null) {
            productDetails.setText(String.format("Name: %s\nCategory: %s\nPrice: %s",
                    product.getName(), product.getCategory(), product.getPrice()));
        } else {
            productDetails.setText("");
        }
    }

    private static void addToCart(String productName) {
        if (productName != null) {
            cartListModel.addElement(productName);
            JOptionPane.showMessageDialog(null, productName + " added to cart!", "Cart", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No product selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class Product {
        private String name;
        private String category;
        private String price;

        public Product(String name, String category, String price) {
            this.name = name;
            this.category = category;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public String getPrice() {
            return price;
        }
    }
}
