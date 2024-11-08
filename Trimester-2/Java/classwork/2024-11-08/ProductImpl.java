class ProductImpl implements Product {
    private double price;

    @Override
    public void print() {
        System.out.println("Product");
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }
}