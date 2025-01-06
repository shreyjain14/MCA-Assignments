package me.shreyjain;

import java.sql.*;

public class EcommerceSQLiteExample {
    // SQLite database connection details
    private static final String URL = "jdbc:sqlite:ecommerce.db";

    public static void main(String[] args) {
        // First, create the database and table if they don't exist
        createDatabase();

        try (Connection conn = DriverManager.getConnection(URL)) {
            System.out.println("Database connected successfully!");

            // Example 1: Add a new product
            addProduct(conn, "Laptop", "High-performance gaming laptop", 999.99, 50);

            // Example 2: Get product by ID
            getProduct(conn, 1);

            // Example 3: Update product stock
            updateProductStock(conn, 1, 45);

            // Example 4: Get all products
            getAllProducts(conn);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    // Create database and table
    public static void createDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS products (
                product_id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                description TEXT,
                price REAL NOT NULL,
                stock INTEGER NOT NULL
            )
            """;

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Database and table created successfully!");

        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }

    // Add a new product
    public static void addProduct(Connection conn, String name, String description,
                                  double price, int stock) throws SQLException {
        String sql = "INSERT INTO products (name, description, price, stock) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, stock);

            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " product(s) inserted successfully!");
        }
    }

    // Get product by ID
    public static void getProduct(Connection conn, int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("\nProduct Details:");
                System.out.println("ID: " + rs.getInt("product_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Price: $" + rs.getDouble("price"));
                System.out.println("Stock: " + rs.getInt("stock"));
            }
        }
    }

    // Update product stock
    public static void updateProductStock(Connection conn, int productId,
                                          int newStock) throws SQLException {
        String sql = "UPDATE products SET stock = ? WHERE product_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newStock);
            pstmt.setInt(2, productId);

            int rowsUpdated = pstmt.executeUpdate();
            System.out.println(rowsUpdated + " product(s) stock updated successfully!");
        }
    }

    // Get all products
    public static void getAllProducts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM products";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nAll Products:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("product_id") +
                        ", Name: " + rs.getString("name") +
                        ", Price: $" + rs.getDouble("price") +
                        ", Stock: " + rs.getInt("stock"));
            }
        }
    }
}