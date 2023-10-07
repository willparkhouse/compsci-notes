/*
* RemoteCalculator . java
* The server main class .
* This server provides a calculator service .
*/
// Import session
// TO BE COMPLETED
public class CalculatorServer {
private int thePort = 0;
private String theIPAddress = null ;
private ServerSocket serverSocket = null ;
// Class constructor
public CalculatorServer () {
// Initialize the TCP socket
// TO BE COMPLETED
// Initialize the socket and runs the
service loop
// TO BE COMPLETED
}
// Runs the service loop
public void executeServiceLoop ()
{
try {
// Service loop
while ( true ) {
// Listening for incoming client
requests .
// TO BE COMPLETED
//A new request has now arrived ;
create a service thread to
attend the request
// The service thread will be
responsible for sending back the
outcome , so
// this service can now " forget "
about that request and move on.
// TO BE COMPLETED
}
} catch ( Exception e ) {
// The creation of the server socket can
cause several exceptions ;
// See
https :// docs . oracle .com/ javase /7/ docs /api/ java /net/ ServeSystem . out . println ( e ) ;
}
System . out . println (" Server : Finished .") ;
System . exit (0) ;
}
public static void main ( String [] args ) {
// Run the server
// TO BE COMPLETED
}
}
