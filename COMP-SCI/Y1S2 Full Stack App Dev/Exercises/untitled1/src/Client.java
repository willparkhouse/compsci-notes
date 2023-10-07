/*
 * Client.java
 *
 * A calculator client
 * Request an input from a user representing a simple addition or product operation,
 * then connects to the server to request the service of the operation, and
 * return the user the outcome of the operation.
 *
 * author: Felipe Orihuela-Espina
 *
 */

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;

import java.util.Scanner;

public class Client {

    private Socket clientSocket = null;

    private String userCommand = null; //The user command
    private String serviceOutcome = null; //The service outcome


    //Class Constructor
    public Client(){
        //Initialize socket and connect to server
        try {
            clientSocket = new Socket("127.0.0.1", 6868);
        }catch(UnknownHostException e){
            System.out.println("Client: Unknown host. " + e);
        }catch(IOException e){
            System.out.println("Client: I/O error. " + e);
        }
    }


    public void requestService() {
        try {
            System.out.println("Client: Requesting calculator service for command '" + this.userCommand + "'");
            OutputStream requestStream = this.clientSocket.getOutputStream();
            OutputStreamWriter requestStreamWriter = new OutputStreamWriter(requestStream);
            requestStreamWriter.write(this.userCommand + "#"); //Add terminator character '#'
            requestStreamWriter.flush(); //Service request sent
        }catch(IOException e){
            System.out.println("Client: I/O error. " + e);
        }
    }


    public void reportServiceOutcome() {
        try {
            InputStream outcomeStream = clientSocket.getInputStream();
            InputStreamReader outcomeStreamReader = new InputStreamReader(outcomeStream);
            StringBuffer stringBuffer = new StringBuffer();
            char x;
            while (true) //Read until terminator character is found
            {
                x = (char) outcomeStreamReader.read();
                if (x == '#')
                    break;
                stringBuffer.append(x);
            }
            this.serviceOutcome = stringBuffer.toString();
            System.out.println("Client: Service outcome: " + this.serviceOutcome);
        }catch(IOException e){
            System.out.println("Client: I/O error. " + e);
        }
    }

    //Execute client
    public void execute(){
        //Prompt the user for an operation:
        Scanner console = new Scanner(System.in);
        System.out.println("Client: Please type your operation in prefix notation e.g. + 4 3:");
        this.userCommand = console.nextLine();

        //Request service
        try{

            //Request service
            this.requestService();

            //Report user outcome of service
            this.reportServiceOutcome();

            //Close the connection with the server
            this.clientSocket.close();

        }catch(Exception e)
        {// Raised if connection is refused or other technical issue
            System.out.println("Client: Exception " + e);
        }
    }

    public static void main (String[] args) {
        try {
            Client theClient = new Client();
            theClient.execute();
            Thread.sleep(3000); //Keep the client alive a bit just to see the action.
        }catch(InterruptedException e){
            System.out.println(e);
        }
        System.out.println("Client: Finished.");
        System.exit(0);
    }
}
