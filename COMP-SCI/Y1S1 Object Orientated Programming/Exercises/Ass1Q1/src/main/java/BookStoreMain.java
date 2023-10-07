import java.time.LocalDate;
import java.util.ArrayList;

public class BookStoreMain {
    public static void main(String[] args) {
        //create a bookStore object and instantiate
        BookStore bookstore = new BookStore();
        //create a customer who can then buy books
        Customer customer1 = new Customer("Jane","0123456","jane@1.com");
        //customer1 places an order to purchase a book
        Stock stock1 = new Stock("DSA", "Alan Sexton", 9250);
        //placing the order ****
        Order order1 = new Order(customer1, stock1);
        //determine the shipping date
        int shipYear1 = 2022;
        int shipMonth1 = 11;
        int shipDay1 = 8;
        LocalDate shipDate = LocalDate.of(shipYear1, shipMonth1, shipDay1);
        Shipping shipOrder1 = new Shipping(order1,shipDate);
        //calculate the shipping cost to send the order
        double shipCost1 = shipOrder1.calcShipCost(true);
        //create an invoice ****
        Invoice invoice1 = new Invoice("DSA001", stock1, shipOrder1);
        //add the invoices to a list so that we can search for an invoice ****
        ArrayList<Invoice> invoices = new ArrayList<Invoice>();
        invoices.add(invoice1);
        //a repeat with another customer, order, etc...

        Customer customer2 = new Customer("dave","098765","DaveyDave@dave.dave");
        Stock stock2 = new Stock("OOP", "Jacqui Chetty", 9250);
        Order order2 = new Order(customer2, stock2);
        int shipYear2 = 2022;
        int shipMonth2 = 12;
        int shipDay2 = 12;
        LocalDate shipDate2 = LocalDate.of(shipYear2, shipMonth2, shipDay2);
        Shipping shipOrder2 = new Shipping(order2,shipDate2);
        double shipCost2 = shipOrder2.calcShipCost(true);
        Invoice invoice2 = new Invoice("OOP001", stock2, shipOrder2);
        invoices.add(invoice2);

        bookstore.pilingUpOfOrders();

        Invoice foundInvoice = bookstore.searchOrder("DSA001");
        //search for order

    }
}

