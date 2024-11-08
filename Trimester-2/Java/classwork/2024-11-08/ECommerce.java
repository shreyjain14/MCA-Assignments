class ECommerce {
    public static void main(String[] args) {
        Product product = new ProductImpl();
        product.setPrice(100);
        product.print();
        System.out.println(product.getPrice());
    }
}