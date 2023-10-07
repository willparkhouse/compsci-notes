//import to be included
import java.time.LocalDate;
public class Shipping {
    private Order order;
    private LocalDate shipDate;
    private double shipCost;
    public static int countUrgent;

    public Shipping(Order order, LocalDate shipDate){
        this.order = order;
        this.shipDate = shipDate;
    }

    public LocalDate getShipDate() {
        return shipDate;
    }

    public double getShipCost() {
        return shipCost;
    }

    public void setShipCost(double shipCost) {
        this.shipCost = shipCost;
    }
    public double calcShipCost(boolean isUrgent){
        if (isUrgent){
            setShipCost(5.45);
            countUrgent = countUrgent + 1;
        }
        if (!isUrgent){
            setShipCost(3.95);
        }
        return shipCost;
    }
}