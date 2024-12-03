public class Order {
    private String id;
    private String productId;
    private String customerId;

    public Order(String id, String productId, String customerId) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
