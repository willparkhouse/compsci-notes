import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {
    Stock stock1;
    Stock stock2;
    @BeforeEach
    void setUp() {
        stock1 = new Stock("Foundation","Isaac Asimov",50.0);
        stock2 = new Stock("Matter","Ian M Banks",25.0);
    }

    @Test
    void getBookName() {
        assertEquals("Foundation", stock1.getBookName());
        assertEquals("Matter", stock2.getBookName());
    }

    @Test
    void getAuthor() {
        assertEquals("Isaac Asimov", stock1.getAuthor());
        assertEquals("Ian M Banks", stock2.getAuthor());
    }

    @Test
    void getPrice() {
        assertEquals(50.0, stock1.getPrice());
        assertEquals(25.0, stock2.getPrice());
    }
}