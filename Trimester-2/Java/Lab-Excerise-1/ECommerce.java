import java.util.List;

class ECommerce {

    public static void main(String[] args) {

        Product product1 = new Product();
        product1.setProduct(1L, "Product 1", "Product 1 Description", 100.0, 10, 1, "product1.jpg");
        product1.printProduct();

        Product product2 = new Product(2L, "Product 2", "Product 2 Description", 200.0, 20, 2, "product2.jpg");
        product2.printProduct();

        Product product3 = new Product(product2);
        product3.printProduct();

        List<Product> products = List.of(product1, product2, product3);
        product1.printProductList(products);

    }

}