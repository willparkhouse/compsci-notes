/*
* CalculatorService . java
* The service threads for the calculator server .
* This class implements the calculator .
* Request are made in prefix notation e.g.
* "+ 4 3"
*/
// Import session
// TO BE COMPLETED
import java.net.*;
public class CalculatorService extends Thread {
    private Socket serverSocket = null;
    private String requestStr = null;
    private String outcome = null;

    // Class constructor
    public CalculatorService ( Socket aSocket ) {
        // Launch the calculator service thread
        // TO BE COMPLETED
    }

    // Retrieve the request from the socket
    public String retrieveRequest () {
        // TO BE COMPLETED
        return this.requestStr;
    }

    // Parse the request command and execute the calculation
    public boolean attendRequest () {
        // TO BE COMPLETED
        // Hint : Use StringTokenizer for parsing the user command and a switch statement to deal with the different operations
        return flagRequestAttended ;
    }

    // Wrap and return service outcome
    public void returnServiceOutcome () {
        // TO BE COMPLETED
    }

    // The service thread run () method
    public void run () {
    try {
    // Retrieve the service request from the socket
        this.retrieveRequest() ;
        System.out.println(" Service thread " + this.getId() + ": Request retrieved : " + this.requestStr) ;
        // Attend the request
        boolean tmp = this.attendRequest() ;
        // Send back the outcome of the request
        if (!tmp) {
            System.out.println(" Service thread " + this.getId() + ": Unable to provide service .") ;
        }
        this.returnServiceOutcome() ;
        }
    catch ( Exception e ) {
        System.out.println(" Service thread " + this.getId() + ": " + e ) ;
    }

    // Terminate service thread (by exiting run () method )
    System.out.println(" Service thread " + this.getId() + ": Finished service .") ;
    }
}