import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {
    Invoice invoice1;
    @BeforeEach
    void setUp() {
        Stock stock = new Stock("Foundation","Isaac Asimov",50.0);
        Customer customer = new Customer("Jack","+441234567","jack@gmail.com");
        Order order = new Order(customer, stock);
        LocalDate shipDate = LocalDate.of(2022, 12, 25);
        Shipping shipping = new Shipping(order, shipDate);
        shipping.calcShipCost(true);
        invoice1 = new Invoice("AAA111",stock,shipping);
    }

    @Test
    void testInvoiceConstructor() {
        Stock testStock = new Stock("Foundation","Isaac Asimov",50.0);
        Customer testCustomer = new Customer("Jack","+441234567","jack@gmail.com");
        Order testOrder = new Order(testCustomer, testStock);
        LocalDate testShipDate = LocalDate.of(2022, 12, 25);
        Shipping testShipping = new Shipping(testOrder, testShipDate);
        testShipping.calcShipCost(true);
        Invoice testInvoice = new Invoice("AAA111",testStock,testShipping);
        assertEquals(testInvoice.getInvoiceNbr(), invoice1.getInvoiceNbr());
        assertEquals(testInvoice.invoice(), invoice1.invoice());
    }

    @Test
    void getInvoiceNbr() {
        assertEquals("AAA111", invoice1.getInvoiceNbr());
    }

    @Test
    void invoice() {
        assertEquals(55.45, invoice1.invoice());
    }

}