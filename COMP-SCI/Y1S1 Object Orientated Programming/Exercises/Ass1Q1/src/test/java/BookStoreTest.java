import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookStoreTest {
    Invoice invoice1;
    Invoice invoice2;
    BookStore bookStore = new BookStore();

    @BeforeEach
    void setUp(){
        Customer customer1 = new Customer("Jane Words", "+4472394586971", "wordsj@gmail.com");
        Stock book1 = new Stock("Death in the afternoon","Hemingway E.", 15.45);
        Order order = new Order(customer1, book1);
        LocalDate shipDate = LocalDate.of(2022, 12, 25);
        Shipping shipping = new Shipping(order, shipDate);
        shipping.calcShipCost(true);
        invoice1 = new Invoice("DIT001",book1,shipping);

        Customer customer2 = new Customer("Safwa Woods", "+447981234582", "woodss@gmail.com");
        Stock book2 = new Stock("Lord of the rings","Tolkien J.R.R.", 12.95);
        Order order2 = new Order(customer2, book2);
        LocalDate shipDate2 = LocalDate.of(2022, 12, 25);
        Shipping shipping2 = new Shipping(order, shipDate);
        shipping.calcShipCost(true);
        invoice2 = new Invoice("LOT111",book2,shipping);

        bookStore.getInvoices().add(invoice1);
        bookStore.getInvoices().add(invoice2);

    }

    @Test
    void bookStore() {
        Customer customer1 = new Customer("Jane Words", "+4472394586971", "wordsj@gmail.com");
        Stock book1 = new Stock("Death in the afternoon","Hemingway E.", 15.45);

        assertEquals("Jane Words",customer1.getName());
        assertEquals("+4472394586971", customer1.getPhone());
        assertEquals("wordsj@gmail.com", customer1.getEmailAddress());
        assertEquals("Death in the afternoon", book1.getBookName());
        assertEquals("Hemingway E.", book1.getAuthor());
        assertEquals(15.45, book1.getPrice());

        Customer customer2 = new Customer("Safwa Woods", "+447981234582", "woodss@gmail.com");
        Stock book2 = new Stock("Lord of the rings","Tolkien J.R.R.", 12.95);

        assertEquals("Safwa Woods",customer2.getName());
        assertEquals("+447981234582", customer2.getPhone());
        assertEquals("woodss@gmail.com", customer2.getEmailAddress());
        assertEquals("Lord of the rings", book2.getBookName());
        assertEquals("Tolkien J.R.R.", book2.getAuthor());
        assertEquals(12.95, book2.getPrice());

    }

    @Test
    void searchOrder() {

        Customer customer1 = new Customer("Jane Words", "+4472394586971", "wordsj@gmail.com");
        Stock book1 = new Stock("Death in the afternoon","Hemingway E.", 15.45);
        Order order = new Order(customer1, book1);
        LocalDate shipDate = LocalDate.of(2022, 12, 25);
        Shipping shipping = new Shipping(order, shipDate);
        shipping.calcShipCost(true);

        assertNotNull(bookStore.searchOrder("DIT001"));
        assertEquals("DIT001",bookStore.searchOrder("DIT001").getInvoiceNbr());
    }

    @Test
    void pilingUpOfOrders() {
        Shipping.countUrgent = 10;
        assertEquals("The urgent orders are piling up .... time to ship quicker", bookStore.pilingUpOfOrders());
    }
}