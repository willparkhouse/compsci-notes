//import of package
import java.util.ArrayList;
public class BookStore {
    //the message has been provided for you - do not change this
    private final String message = "The urgent orders are piling up .... time to ship quicker";
    //create the invoices ArrayList<>
    private ArrayList<Invoice> invoices;
    private String piledUp;
    private Invoice found;
    public BookStore() {
        invoices = new ArrayList<Invoice>();
    }
    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }
    public Invoice searchOrder(String invoiceNbr){
        found = null;
        int i;
        for(i = 0; i < invoices.size(); i++){
            if (invoices.get(i).getInvoiceNbr() == invoiceNbr){
                found = invoices.get(i);
            } else {
                System.out.println("Invoice number not found.");
            }
        }
        return found;
    }
    public String pilingUpOfOrders(){

        if (Shipping.countUrgent > 5){
            piledUp = message;
        } else{
            piledUp = null;
        }
        return piledUp;
    }
    //complete the constructor
    //getter() for invoices list

    //search for an order

    //piling up of orders
}
