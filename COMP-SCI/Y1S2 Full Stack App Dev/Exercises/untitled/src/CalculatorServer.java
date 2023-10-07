/*
* RemoteCalculator . java
* The server main class .
* This server provides a calculator service .
*/
// Import session
import java.io.IOException;
import java.net.*;
public class CalculatorServer {
    private int thePort = 0;
    private String theIPAddress = "127.0.0.1";
    private ServerSocket serverSocket = null ;
    // Class constructor
    public CalculatorServer () throws IOException {
        // Initialize the TCP socket
        InetAddress ip = InetAddress.getByName(theIPAddress);
        serverSocket = new ServerSocket(thePort, 5, ip);
        // TO BE COMPLETED
        // Initialize the socket and runs the service loop
        Socket socket = serverSocket.accept();
        // TO BE COMPLETED
    }
    // Runs the service loop
    public void executeServiceLoop() {
        try { // Service loop
            while ( true ) {
            // Listening for incoming client requests .
            // TO BE COMPLETED
            //A new request has now arrived ; create a service thread to attend the request
            // The service thread will be responsible for sending back the outcome , so
            // this service can now " forget " about that request and move on.
            // TO BE COMPLETED
            }
        } catch ( Exception e ) {
            // The creation of the server socket can cause several exceptions ;
            // See https :// docs . oracle .com/ javase /7/ docs /api/ java /net/ Serve
            System.out.println(e);
        }
        System.out.println(" Server : Finished .") ;
        System.exit(0) ;
    }
public static void main ( String [] args ) {
    // Run the server
    // TO BE COMPLETED
    }
}
