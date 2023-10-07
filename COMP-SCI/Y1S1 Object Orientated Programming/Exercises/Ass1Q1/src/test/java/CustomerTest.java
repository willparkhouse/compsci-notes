import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    Customer customer1;
    Customer customer2;
    @BeforeEach
    void setUp() {
        customer1 = new Customer("Jack","+441234567","jack@gmail.com");
        customer2 = new Customer("Jill","+449876543","");
    }

    @Test
    void testCustomerConstructor() {
       Customer customerTest = new Customer("Jack","+441234567","jack@gmail.com");
       assertEquals(customerTest.getName(),customer1.getName());
       assertEquals(customerTest.getPhone(),customer1.getPhone());
       assertEquals(customerTest.getEmailAddress(),customer1.getEmailAddress());
    }

    @Test
    void getName() {
       assertEquals("Jack", customer1.getName());
        assertEquals("Jill", customer2.getName());
    }

    @Test
    void getPhone() {
        assertEquals("+441234567", customer1.getPhone());
        assertEquals("+449876543", customer2.getPhone());
    }

    @Test
    void getEmailAddress() {
        assertEquals("jack@gmail.com", customer1.getEmailAddress());
        assertEquals("", customer2.getEmailAddress());
    }
}