import java.util.Date;
import java.util.List;

class Order {

    private Long id;
    private String orderNumber;

    private User user;

    private List<Product> products;
    private String status;
    private Date date;
    private Double total;
    private Double tax;
    private Double shipping;
    private Double discount;
    private Double grandTotal;
    private Boolean paid;

}