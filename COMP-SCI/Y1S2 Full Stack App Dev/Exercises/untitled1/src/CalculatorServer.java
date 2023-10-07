/*
 * RemoteCalculator.java
 *
 * The server main class.
 * This server provides a calculator service.
 *
 * author: Felipe Orihuela-Espina
 *
 */

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;

public class CalculatorServer{

    private int thePort = 0;
    private String theIPAddress = null;
    private ServerSocket serverSocket =  null;


    //Class constructor
    public CalculatorServer(){
        //Initialize the TCP socket
        thePort = 6868;
        theIPAddress = "127.0.0.1";

        //Initialize the socket and runs the service loop
        try {
            //Initialize the socket
            int maxConnectionQueue = 3;
            serverSocket = new ServerSocket(thePort,maxConnectionQueue, InetAddress.getByName(theIPAddress));
            System.out.println("Server at " + theIPAddress + " is listening on port : " + this.thePort);
        } catch (Exception e){
            //The creation of the server socket can cause several exceptions;
            //See https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html
            System.out.println(e);
            System.exit(1);
        }
    }

    //Runs the service loop
    public void executeServiceLoop()
    {
        try {
            //Service loop
            while (true) {
                //Listening for incoming client requests.
                Socket aSocket = this.serverSocket.accept(); //The server waits here until a new request arrives
                            //Wath out with the implicit casting from ServerSocket to Socket

                //A new request has now arrived; create a service thread to attend the request
                //The service thread will be responsible for sending back the outcome, so
                //this service can now "forget" about that request and move on.
                CalculatorService tmpServiceThread = new CalculatorService(aSocket);

            }
        } catch (Exception e){
            //The creation of the server socket can cause several exceptions;
            //See https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html
            System.out.println(e);
        }
        System.out.println("Server: Finished.");
        System.exit(0);
    }



    public static void main(String[] args){
        //Run the server
        CalculatorServer server=new CalculatorServer(); //inc. Initializing the socket
        server.executeServiceLoop();
    }


}
