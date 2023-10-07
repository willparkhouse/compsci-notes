public class Order {
    //attributes
    private Customer customer;
    private Stock stock;
    public Order(Customer customer, Stock stock) {
        this.customer = customer;
        this.stock = stock;
    }

    //complete the getters()
    public Customer getCustomer() {
        return customer;
    }

    public Stock getStock() {
        return stock;
    }
}
