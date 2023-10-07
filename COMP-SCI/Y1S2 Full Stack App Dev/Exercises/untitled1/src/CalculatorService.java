/*
 * CalculatorService.java
 *
 * The service threads for the calculator server.
 * This class implements the calculator.
 * Request are made in prefix notation e.g.
 *  "+ 4 3"
 *
 * author: Felipe Orihuela-Espina
 *
 */

//import com.sun.deploy.security.SelectableSecurityManager;

import java.io.*;
import java.net.Socket;

import java.util.StringTokenizer;


public class CalculatorService extends Thread{

    private Socket serverSocket =  null;
    private String requestStr = null;
    private String outcome = null;

    //Class constructor
    public CalculatorService(Socket aSocket){
        serverSocket = aSocket;

        //Launch the calculator service thread
        this.start();
    }


    //Retrieve the request from the socket
    public String retrieveRequest()
    {
        this.requestStr = "";
        try {

            InputStream socketStream = this.serverSocket.getInputStream();
            InputStreamReader socketReader = new InputStreamReader(socketStream);
            StringBuffer stringBuffer = new StringBuffer();
            char x;
            while (true) //Read until terminator character is found
            {
                x = (char) socketReader.read();
                if (x == '#')
                    break;
                stringBuffer.append(x);
            }
            this.requestStr = stringBuffer.toString();
         }catch(IOException e){
            System.out.println("Service thread " + this.getId() + ": " + e);
        }
        return this.requestStr;
    }


    //Parse the request command and execute the calculation
    public boolean attendRequest()
    {
        boolean flagRequestAttended = true;

        this.outcome = "Null";


        StringTokenizer st1 = new StringTokenizer(this.requestStr);
        String operator = st1.nextToken();

        double[] operands = new double[10]; //Up to 10 operands
        double res = 0;

        switch (operator) {
            case "+":
                System.out.println("Service thread " + this.getId() + ": Addition operation found.");
                //Read operands
                operands[0] = Double.parseDouble(st1.nextToken());
                operands[1] = Double.parseDouble(st1.nextToken());
                res = operands[0] + operands[1];
                this.outcome = "" + res;
                break;

            case "*":
                System.out.println("Service thread " + this.getId() + ": Product operation found.");
                operands[0] = Double.parseDouble(st1.nextToken());
                operands[1] = Double.parseDouble(st1.nextToken());
                res = operands[0] * operands[1];
                this.outcome = "" + res;
                break;

            default:
                this.outcome = "Unknown operator '" + operator + "'.";
                System.out.println("Service thread " + this.getId() + ": " + this.outcome);
                flagRequestAttended = false;
        }
        return flagRequestAttended;
    }



    //Wrap and return service outcome
    public void returnServiceOutcome(){
        try {
            OutputStream outcomeStream = this.serverSocket.getOutputStream();
            OutputStreamWriter outcomeStreamWriter = new OutputStreamWriter(outcomeStream);
            outcomeStreamWriter.write(this.outcome + "#"); //Wrap and add termination character
            outcomeStreamWriter.flush(); // Return outcome
            System.out.println("Service thread " + this.getId() + ": Service outcome returned; " + this.outcome);
            this.serverSocket.close(); //terminating connection
        }catch (IOException e){
            System.out.println("Service thread " + this.getId() + ": " + e);
        }
    }


    //The service thread run() method
    public void run()
    {
        try {
            //Retrieve the service request from the socket
            this.retrieveRequest();
            System.out.println("Service thread " + this.getId() + ": Request retrieved: " + this.requestStr);

            //Attend the request
            boolean tmp = this.attendRequest();

            //Send back the outcome of the request
            if (!tmp)
                System.out.println("Service thread " + this.getId() + ": Unable to provide service.");
            this.returnServiceOutcome();

        }catch (Exception e){
            System.out.println("Service thread " + this.getId() + ": " + e);
        }
        //Terminate service thread (by exiting run() method)
        System.out.println("Service thread " + this.getId() + ": Finished service.");
    }

}
