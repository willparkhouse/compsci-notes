/*
 * Romeo.java
 *
 * Romeo class.  Implements the Romeo subsystem of the Romeo and Juliet ODE system.
 */


import java.io.*;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetAddress;

import javafx.util.Pair;

public class Romeo extends Thread {

    private ServerSocket ownServerSocket = null; //Romeo's (server) socket
    private Socket serviceMailbox = null; //Romeo's (service) socket


    private double currentLove = 0;
    private double a = 0; //The ODE constant

    //Class construtor
    public Romeo(double initialLove) {
        currentLove = initialLove;
        a = 0.02;
        try {
            ownServerSocket = new ServerSocket(7778, 3, InetAddress.getByName("127.0.0.1"));
			//TO BE COMPLETED

            System.out.println("Romeo: What lady is that, which doth enrich the hand\n" +
                    "       Of yonder knight?");
        } catch(Exception e) {
            System.out.println("Romeo: Failed to create own socket " + e);
        }
   }

    //Get acquaintance with lover;
    public Pair<InetAddress,Integer> getAcquaintance() {
        System.out.println("Romeo: Did my heart love till now? forswear it, sight! For I ne'er saw true beauty till this night.");

			//TO BE COMPLETED
        try {
            //Socket socket = ownServerSocket.accept();
            //InetAddress serviceAddress = socket.getInetAddress();
            //int servicePort = socket.getPort();
            //serviceMailbox = new Socket(serviceAddress, servicePort);
            Pair<InetAddress, Integer> romeoConnection = new Pair<>(ownServerSocket.getInetAddress(), ownServerSocket.getLocalPort());
            return romeoConnection;
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Romeo failed to get acquaintance.");
            return null;
        }
    }


    //Retrieves the lover's love
    public double receiveLoveLetter()
    {
        //TO BE COMPLETED
        try {
            serviceMailbox = ownServerSocket.accept();
            InputStream is = serviceMailbox.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = isr.read()) != -1) {
                if (c == '#') {
                    break;
                }
                sb.append((char) c);
            }
            double tmp = Double.parseDouble(sb.toString());
            System.out.println("Romeo: O sweet Juliet... (<-" + tmp + ")");
            return tmp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //Love (The ODE system)
    //Given the lover's love at time t, estimate the next love value for Romeo
    public double renovateLove(double partnerLove){
        System.out.println("Romeo: But soft, what light through yonder window breaks?\n" +
                "       It is the east, and Juliet is the sun.");
        currentLove = currentLove+(a*partnerLove);
        return currentLove;
    }


    //Communicate love back to playwriter
    public void declareLove(){
			//TO BE COMPLETED
			try{
                PrintWriter pw = new PrintWriter(serviceMailbox.getOutputStream(), true);
                String trace = "R";
                String declaration = "Romeo: I would I were thy bird.";
                String combined = declaration + " (->" + currentLove + trace + ")";
                System.out.println(combined);
                pw.println(combined);
                //serviceMailbox.shutdownOutput();
            } catch (Exception e){
                System.out.println("error declare love");
            }
    }



    //Execution
    public void run () {
        try {
            while (!this.isInterrupted()) {
                //Retrieve lover's current love
                double JulietLove = this.receiveLoveLetter();
                //Estimate new love value
                this.renovateLove(JulietLove);
                //Communicate love back to playwriter
                this.declareLove();
            }
        }catch (Exception e){
            System.out.println("Romeo: " + e);
        }
        if (this.isInterrupted()) {
            System.out.println("Romeo: Here's to my love. O true apothecary,\n" +
                    "Thy drugs are quick. Thus with a kiss I die." );
        }
    }

}
