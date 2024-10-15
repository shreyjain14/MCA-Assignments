class ECommerce {

    public static void main(String[] args) {

        Product product1 = new Product(1L, "Product 1", 100.0);
        product1.display();

        Product product2 = new Product("Product 2", 200.0);
        product2.display();

        Product product3 = new Product(product2);
        product3.display();

    }

}