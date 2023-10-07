import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ShippingTest {

    Shipping shipping1;

    @BeforeEach
    void setUp() {
        Stock stock = new Stock("Foundation","Isaac Asimov",50.0);
        Customer customer = new Customer("Jack","+441234567","jack@gmail.com");
        Order order = new Order(customer, stock);
        LocalDate shipDate = LocalDate.of(2022, 12, 25);
        shipping1 = new Shipping(order,shipDate);
    }

    @Test
    void getShipDate() {
        assertEquals(shipping1.getShipDate(), LocalDate.of(2022, 12, 25));
    }

    @Test
    void getShipCost() {
        shipping1.setShipCost(11);
        assertEquals(11, shipping1.getShipCost());
    }

    @Test
    void calcShipCost() {
        assertEquals(5.45, shipping1.calcShipCost(true));
        assertEquals(3.95, shipping1.calcShipCost(false));
    }

}