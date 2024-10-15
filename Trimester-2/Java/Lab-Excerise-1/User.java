import java.util.List;

class User {

    private Long id;
    private String name;
    private String email;
    private String phone;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zip;
    private String country;

    private List<Product> cart;
    private List<Order> orders;
    private List<Review> reviews;

}