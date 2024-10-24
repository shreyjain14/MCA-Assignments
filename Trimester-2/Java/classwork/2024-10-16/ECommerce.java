class ECommerce {

    public static void main(String[] args) {

        Product product = new ProductImpl();
        product.print();
        product.buy();

        ProductImpl productImpl = new ProductImpl();
        productImpl.print();
        productImpl.buy();

    }

}