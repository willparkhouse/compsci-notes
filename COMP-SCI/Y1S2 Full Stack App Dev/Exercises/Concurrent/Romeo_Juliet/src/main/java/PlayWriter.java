/*
 * PlayWriter.java
 *
 * PLayWriter class.
 * Creates the lovers, and writes the two lover's story (to an output text file).
 */


import java.io.*;
import java.net.Socket;
import java.net.InetAddress;

import javafx.util.Pair;


public class PlayWriter {

    private Romeo  myRomeo  = null;
    private InetAddress RomeoAddress = null;
    private int RomeoPort = 0;
    private Socket RomeoMailbox = null;

    private Juliet myJuliet = null;
    private InetAddress JulietAddress = null;
    private int JulietPort = 0;
    private Socket JulietMailbox = null;

    double[][] theNovel = null;
    int novelLength = 0;

    public PlayWriter()
    {
        novelLength = 500; //Number of verses
        theNovel = new double[novelLength][2];
        theNovel[0][0] = 0;
        theNovel[0][1] = 1;
    }

    //Create the lovers
    public void createCharacters() {
        //Create the lovers
        System.out.println("PlayWriter: Romeo enters the stage.");
        myRomeo = new Romeo(0);
        myRomeo.start();
			//TO BE COMPLETED
        System.out.println("PlayWriter: Juliet enters the stage.");
			//TO BE COMPLETED
        myJuliet = new Juliet(1);
        myJuliet.start();
			
    }

    //Meet the lovers and start letter communication
    public void charactersMakeAcquaintances() {

        Pair<InetAddress, Integer> romeoConn = myRomeo.getAcquaintance();
        RomeoAddress = romeoConn.getKey();
        RomeoPort = romeoConn.getValue();
        System.out.println("PlayWriter: I've made acquaintance with Romeo");

        Pair<InetAddress, Integer> julietConn = myJuliet.getAcquaintance();
        JulietAddress = julietConn.getKey();
        JulietPort = julietConn.getValue();
        System.out.println("PlayWriter: I've made acquaintance with Juliet");
    }


    //Request next verse: Send letters to lovers communicating the partner's love in previous verse
    public void requestVerseFromRomeo(int verse) {
        System.out.println("PlayWriter: Requesting verse " + verse + " from Romeo. -> (" + theNovel[verse - 1][1] + ")");
        try {
            RomeoMailbox = new Socket(RomeoAddress, RomeoPort);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(RomeoMailbox.getOutputStream()));
            writer.write(theNovel[verse-1][1] + "#");
            writer.flush();
            System.out.println("Romeo: Letter received");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


        //Request next verse: Send letters to lovers communicating the partner's love in previous verse
    public void requestVerseFromJuliet(int verse) {
        System.out.println("PlayWriter: Requesting verse " + verse + " from Juliet. -> (" + theNovel[verse-1][0] + ")");
        try {
            JulietMailbox = new Socket(JulietAddress, JulietPort);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(JulietMailbox.getOutputStream()));
            writer.write(theNovel[verse-1][0] + "#");
            writer.flush();
            System.out.println("Juliet: Letter received");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //Receive letter from Romeo with renovated love for current verse
    public void receiveLetterFromRomeo(int verse) {
        try {
            System.out.println("Romeo: Entering service iteration.");
            BufferedReader loveReader = new BufferedReader(new InputStreamReader(RomeoMailbox.getInputStream()));
            String rawIn = "";
            String message = "";
            String nextLine = loveReader.readLine();
            while (nextLine != null) {
                rawIn += nextLine;
                if(nextLine.indexOf(')') > 0){
                    loveReader.mark(0);
                    break;
                }
                nextLine = loveReader.readLine();
            }
            String[] parts = rawIn.split(">");
            message = parts[parts.length-1].split("R")[0];
            theNovel[verse][0] = Double.parseDouble(message);
            loveReader.reset();
            RomeoMailbox.close();
        } catch (IOException e) {
            System.out.println("Failed to send service request to Romeo.");
            throw new RuntimeException(e);
        }
        System.out.println("PlayWriter: Romeo's verse " + verse + " -> " + theNovel[verse][0]);
    }

    //Receive letter from Juliet with renovated love fro current verse
    public void receiveLetterFromJuliet(int verse) {
        try {

            BufferedReader loveReader = new BufferedReader(new InputStreamReader(JulietMailbox.getInputStream()));
            String rawIn = "";
            String message = "";
            String nextLine = loveReader.readLine();
            while (nextLine != null) {
                rawIn += nextLine;
                if(nextLine.indexOf(')') > 0){
                    loveReader.mark(0);
                    break;
                }
                nextLine = loveReader.readLine();
            }
            String[] parts = rawIn.split(">");
            message = parts[parts.length-1].split("J")[0];
            theNovel[verse][1] = Double.parseDouble(message);
            JulietMailbox.close();
        } catch (IOException e) {
            System.out.println("Failed to send service request to Juliet.");
            throw new RuntimeException(e);
        }
        System.out.println("PlayWriter: Juliet's verse " + verse + " -> " + theNovel[verse][1]);
    }

    //Let the story unfold
    public void storyClimax() {
        for (int verse = 1; verse < novelLength; verse++) {
            //Write verse
            System.out.println("PlayWriter: Writing verse " + verse + ".");
            this.requestVerseFromRomeo(verse);
            this.receiveLetterFromRomeo(verse);
            this.requestVerseFromJuliet(verse);
            this.receiveLetterFromJuliet(verse);
            System.out.println("PlayWriter: Verse " + verse + " finished.");
        }
    }

    //Character's death
    public void charactersDeath() {
            myRomeo.interrupt();
            myJuliet.interrupt();
    }


    //A novel consists of introduction, conflict, climax and denouement
    public void writeNovel() {
        System.out.println("PlayWriter: The Most Excellent and Lamentable Tragedy of Romeo and Juliet.");
        System.out.println("PlayWriter: A play in IV acts.");
        //Introduction,
        System.out.println("PlayWriter: Act I. Introduction.");
        this.createCharacters();
        //Conflict
        System.out.println("PlayWriter: Act II. Conflict.");
        this.charactersMakeAcquaintances();
        //Climax
        System.out.println("PlayWriter: Act III. Climax.");
        this.storyClimax();
        //Denouement
        System.out.println("PlayWriter: Act IV. Denouement.");
        this.charactersDeath();

    }


    //Dump novel to file
    public void dumpNovel() {
        FileWriter Fw = null;
        try {
            Fw = new FileWriter("RomeoAndJuliet.csv");
        } catch (IOException e) {
            System.out.println("PlayWriter: Unable to open novel file. " + e);
        }

        System.out.println("PlayWriter: Dumping novel. ");
        StringBuilder sb = new StringBuilder();
        for (int act = 0; act < novelLength; act++) {
            String tmp = theNovel[act][0] + ", " + theNovel[act][1] + "\n";
            sb.append(tmp);
            //System.out.print("PlayWriter [" + act + "]: " + tmp);
        }

        try {
            BufferedWriter br = new BufferedWriter(Fw);
            br.write(sb.toString());
            br.close();
        } catch (Exception e) {
            System.out.println("PlayWriter: Unable to dump novel. " + e);
        }
    }

    public static void main (String[] args) {
        PlayWriter Shakespeare = new PlayWriter();
        Shakespeare.writeNovel();
        Shakespeare.dumpNovel();
        System.exit(0);
    }
}
