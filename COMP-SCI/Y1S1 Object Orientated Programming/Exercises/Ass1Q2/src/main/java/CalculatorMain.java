public class CalculatorMain {
    static boolean headsUp = false;

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        calc.setExpression("600 * 12");
        headsUp = calc.verify();
        if (headsUp)
            System.out.println(calc.evaluate());
        else
            System.out.println("Invalid equation inserted");
    }
}

