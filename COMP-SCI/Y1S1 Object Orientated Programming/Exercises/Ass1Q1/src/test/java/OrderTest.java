import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    Order order1;
    @BeforeEach
    void setUp() {
        Stock stock = new Stock("Foundation","Isaac Asimov",50.0);
        Customer customer = new Customer("Jack","+441234567","jack@gmail.com");
        order1 = new Order(customer, stock);
    }

    @Test
    void testOrderConstructor() {
        Customer customerTest = new Customer("Jack","+441234567","jack@gmail.com");
        Stock stockTest = new Stock("Foundation","Isaac Asimov",50.0);
        Order orderTest = new Order(customerTest, stockTest);
        assertEquals(orderTest.getCustomer().getName(), order1.getCustomer().getName());
        assertEquals(orderTest.getStock().getBookName(), order1.getStock().getBookName());
    }

    @Test
    void getCustomer() {
        assertEquals("Jack", order1.getCustomer().getName());
        assertEquals("+441234567", order1.getCustomer().getPhone());
        assertEquals("jack@gmail.com", order1.getCustomer().getEmailAddress());
    }

    @Test
    void getStock() {
        assertEquals("Foundation", order1.getStock().getBookName());
        assertEquals("Isaac Asimov", order1.getStock().getAuthor());
        assertEquals(50.0, order1.getStock().getPrice());
    }

}