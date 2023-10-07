/*
 * BooksDatabaseServer.java
 *
 * The server main class.
 * This server provides a service to access the Books database.
 *
 * author: <YOUR STUDENT ID HERE>
 *
 */

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;



public class BooksDatabaseServer {

    private int thePort = 0;
    private String theIPAddress = null;
    private ServerSocket serverSocket =  null;
	
	//Support for closing the server
	//private boolean keypressedFlag = false;
	

    //Class constructor
    public BooksDatabaseServer(){
        //Initialize the TCP socket
        thePort = Credentials.PORT;
        theIPAddress = Credentials.HOST;

        //Initialize the socket and runs the service loop
        System.out.println("Server: Initializing server socket at " + theIPAddress + " with listening port " + thePort);
        System.out.println("Server: Exit server application by pressing Ctrl+C (Windows or Linux) or Opt-Cmd-Shift-Esc (Mac OSX)." );
        try {
            //Initialize the socket

            //TO BE COMPLETED
            serverSocket = new ServerSocket(thePort, 3, InetAddress.getByName(theIPAddress));



            System.out.println("Server: Server at " + theIPAddress + " is listening on port : " + thePort);
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
        System.out.println("Server: Start service loop.");
        try {
            //Service loop
            while (true) {
				
                //TO BE COMPLETED
                Socket clientSocket = serverSocket.accept(); // block until a client request arrives
                Runnable DBService = new BooksDatabaseService(clientSocket);
                Thread serviceThread = new Thread(DBService);
                serviceThread.start();

            }
        } catch (Exception e){
            //The creation of the server socket can cause several exceptions;
            //See https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html
            System.out.println(e);
        }
        System.out.println("Server: Finished service loop.");
    }


/*
	@Override
	protected void finalize() {
		//If this server has to be killed by the launcher with destroyForcibly
		//make sure we also kill the service threads.
		System.exit(0);
	}
*/
	
    public static void main(String[] args){
        //Run the server
        BooksDatabaseServer server=new BooksDatabaseServer(); //inc. Initializing the socket
        server.executeServiceLoop();
        System.out.println("Server: Finished.");
        System.exit(0);
    }


}
