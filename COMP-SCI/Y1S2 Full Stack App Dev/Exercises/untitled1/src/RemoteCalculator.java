/*
 * RemoteCalculator.java
 *
 * A class to hold the main method and launch the server and clients.
 *
 * author: Felipe Orihuela-Espina
 *
 */

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RemoteCalculator {


    public static void main (String[] args)
    {
        try {
            //Create and launch the server
            System.out.println("MAIN: Launching Calculator server. Server will be alive for about 1 minute.");
            Process serverProcess = Runtime.getRuntime().exec("cmd /c start \"CalculatorServer\" /D .\\out\\production\\W04_Ex1_RemoteCalculator\\ cmd /k java CalculatorServer");
                //NOTE: First cmd \c does NOT open a window; but just execute the command. It is actually the second cmd which keeps the command window opened.

            //Create and launch clients
            System.out.println("MAIN: Launching clients.");
            Process client1Process = Runtime.getRuntime().exec("cmd /c start \"CalculatorServer\" /D .\\out\\production\\W04_Ex1_RemoteCalculator\\ cmd /k java Client");

            //Terminate the server (avoid the eternal loop)
            serverProcess.waitFor(30000, TimeUnit.MILLISECONDS);
            System.out.println("MAIN: Server timed out. Killing server.");
            serverProcess.destroyForcibly();

            client1Process.waitFor(1000, TimeUnit.MILLISECONDS); //Kill the client process if still there...
            client1Process.destroy();

        }catch(InterruptedException e){
            System.out.println("MAIN: Interrupted. " + e);
        }catch(IOException e){
        System.out.println("MAIN: I/O Error. " + e);
        }
        System.out.println("MAIN: Finished simulation.");
        System.exit(0);
    }
}
