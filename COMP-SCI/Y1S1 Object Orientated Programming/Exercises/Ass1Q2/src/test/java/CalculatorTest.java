import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CalculatorTest {
    Calculator calculator;
    @BeforeEach
    void setUp() {
        calculator = new Calculator();

    }

    @Test //10 marks
    void verify() {
        calculator.setExpression("100 + 100");
        assertEquals(true, calculator.verify());
        calculator.setExpression("100 - 100");
        assertEquals(true, calculator.verify());
        calculator.setExpression("200+200");
        assertEquals(false, calculator.verify());
        calculator.setExpression("200 * 200");
        assertEquals(true, calculator.verify());
    }

    @Test //5 marks
    void verifyDivideByZero() {
        calculator.setExpression("100 / 0");
        assertEquals(false, calculator.verify());
        calculator.setExpression("0 / 100");
        assertEquals(true, calculator.verify());

    }

    @Test //10 marks
    void evaluate() {
        calculator.setExpression("100 + 100");
        calculator.verify();
        assertEquals(200, calculator.evaluate());
        calculator.setExpression("100 - 100");
        calculator.verify();
        assertEquals(0, calculator.evaluate());
        calculator.setExpression("200 * 200");
        calculator.verify();
        assertEquals(40000, calculator.evaluate());
        calculator.setExpression("200 / 5");
        calculator.verify();
        assertEquals(40, calculator.evaluate());
    }



    @Test //5marks
    void getOperator() {
        calculator.setExpression("100 + 100");
        calculator.verify();
        assertEquals('+', calculator.getOperator());

        calculator.setExpression("100 - 100");
        calculator.verify();
        assertEquals('-', calculator.getOperator());

        calculator.setExpression("100 * 100");
        calculator.verify();
        assertEquals('*', calculator.getOperator());

        calculator.setExpression("100 / 100");
        calculator.verify();
        assertEquals('/', calculator.getOperator());
    }

    @Test //5 marks
    void getOperand1() {
        calculator.setExpression("100 + 100");
        calculator.verify();
        assertEquals(100, calculator.getOperand1());

        calculator.setExpression("200 + 100");
        calculator.verify();
        assertEquals(200, calculator.getOperand1());

        calculator.setExpression("10 + 100");
        calculator.verify();
        assertEquals(10, calculator.getOperand1());

        calculator.setExpression("123 + 100");
        calculator.verify();
        assertEquals(123, calculator.getOperand1());

    }

    @Test //5 marks
    void getOperand2() {
        calculator.setExpression("100 + 100");
        calculator.verify();
        assertEquals(100, calculator.getOperand2());

        calculator.setExpression("200 * 10");
        calculator.verify();
        assertEquals(10, calculator.getOperand2());

        calculator.setExpression("10 + 300");
        calculator.verify();
        assertEquals(300, calculator.getOperand2());

        calculator.setExpression("123 - 6700");
        calculator.verify();
        assertEquals(6700, calculator.getOperand2());
    }

}
