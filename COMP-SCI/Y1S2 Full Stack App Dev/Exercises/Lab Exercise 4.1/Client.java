/*
* Client . java
* A calculator client
* Request an input from a user representing a
simple addition or product operation ,
* then connects to the server to request the
service of the operation , and
* return the user the outcome of the operation .
*/
// Import session
// TO BE COMPLETED
public class Client {
private Socket clientSocket = null ;
private String userCommand = null ; // The user
command
private String serviceOutcome = null ; // The
service outcome
// Class Constructor
public Client () {
// Initialize socket and connect to server
// TO BE COMPLETED
}
public void requestService () {
// TO BE COMPLETED
// Add terminator character ’\# ’ to the
service request message
}
public void reportServiceOutcome () {
// TO BE COMPLETED
}
// Execute client
public void execute () {
// Prompt the user for an operation :
// TO BE COMPLETED
// Request service
try{
// Request service
this . requestService () ;
// Report user outcome of service
this . reportServiceOutcome () ;
// Close the connection with the server
this . clientSocket . close () ;
} catch ( Exception e )
{// Raised if connection is refused or
other technical issue
System . out . println (" Client : Exception "
+ e ) ;
}
}
public static void main ( String [] args ) {
// TO BE COMPLETED
System . out . println (" Client : Finished .") ;
System . exit (0) ;
}
}

