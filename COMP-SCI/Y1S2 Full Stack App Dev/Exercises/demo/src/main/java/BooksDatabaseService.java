/*
 * BooksDatabaseService.java
 *
 * The service threads for the books database server.
 * This class implements the database access service, i.e. opens a JDBC connection
 * to the database, makes and retrieves the query, and sends back the result.
 *
 * author: <YOUR STUDENT ID HERE>
 *
 */

import com.Credentials;

import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.net.Socket;

import java.sql.*;
import javax.sql.rowset.*;
    //Direct import of the classes CachedRowSet and CachedRowSetImpl will fail becuase
    //these clasess are not exported by the module. Instead, one needs to impor
    //javax.sql.rowset.* as above.



public class BooksDatabaseService extends Thread{

    private Socket serviceSocket = null;
    private String[] requestStr  = new String[2]; //One slot for author's name and one for library's name.
    private ResultSet outcome   = null;

	//JDBC connection
    private String USERNAME = Credentials.USERNAME;
    private String PASSWORD = Credentials.PASSWORD;
    private String URL      = Credentials.URL;



    //Class constructor
    public BooksDatabaseService(Socket aSocket){

		//TO BE COMPLETED
        this.serviceSocket = aSocket;
        this.start();
    }


    //Retrieve the request from the socket
    public String[] retrieveRequest()
    {
        this.requestStr[0] = ""; //For author
        this.requestStr[1] = ""; //For library

        String tmp = "";
        try {

			//TO BE COMPLETED
            InputStream is = serviceSocket.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            int data = reader.read();
            String requestText = new String();
            while (data != '#') {
                requestText = requestText + ((char) data);
                data = reader.read();
            }
            String[] splitSemi = requestText.split(";");
            this.requestStr[0] = splitSemi[0];
            this.requestStr[1] = splitSemi[1];


        }catch(IOException e){
            System.out.println("Service thread " + this.getId() + ": " + e);
        }
        return this.requestStr;
    }


    //Parse the request command and execute the query
    public boolean attendRequest()
    {
        boolean flagRequestAttended = true;
		
		this.outcome = null;
		
		String sql = ""; //TO BE COMPLETED- Update this line as needed.
		
		
		try {
			//Connet to the database
			//TO BE COMPLETED
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			//Make the query
			//TO BE COMPLETED
            sql = "SELECT title, publisher, genre, rrp, count(*) FROM (author INNER JOIN book ON book.authorid = author.authorid INNER JOIN bookcopy ON book.bookid = bookcopy.bookid INNER JOIN library ON bookcopy.libraryid = library.libraryid) WHERE author.familyname = '?' AND library.city='?' AND bookcopy.onloan = false GROUP BY book.bookid;";
            PreparedStatement queryStatement = conn.prepareStatement(String.format(sql));
            queryStatement.setString(1, this.requestStr[0]);
            queryStatement.setString(2, this.requestStr[1]);
            //Process query
            outcome = queryStatement.executeQuery();
			//TO BE COMPLETED -  Watch out! You may need to reset the iterator of the row set.
            RowSetFactory aFactory = RowSetProvider.newFactory();
            CachedRowSet crs = aFactory.createCachedRowSet();
            crs.populate(outcome);
            outcome=crs;
            conn.close();
            queryStatement.close();
            outcome.close();

			//Clean up
			//TO BE COMPLETED
			
		} catch (Exception e)
		{ System.out.println(e); }

        return flagRequestAttended;
    }



    //Wrap and return service outcome
    public void returnServiceOutcome(){
        try {
			//Return outcome
			//TO BE COMPLETED
            ObjectOutputStream oos = new ObjectOutputStream(serviceSocket.getOutputStream());
            oos.writeObject(outcome);
            oos.flush();
            System.out.println("Service thread " + this.getId() + ": Service outcome returned; " + this.outcome);
            
			//Terminating connection of the service socket
			//TO BE COMPLETED
			serviceSocket.close();
			
        }catch (IOException e){
            System.out.println("Service thread " + this.getId() + ": " + e);
        }
    }


    //The service thread run() method
    public void run()
    {
		try {
			System.out.println("\n============================================\n");
            //Retrieve the service request from the socket
            this.retrieveRequest();
            System.out.println("Service thread " + this.getId() + ": Request retrieved: "
						+ "author->" + this.requestStr[0] + "; library->" + this.requestStr[1]);

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
