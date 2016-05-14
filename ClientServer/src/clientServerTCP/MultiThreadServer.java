package clientServerTCP;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

/* CS5800_Wednesday_12:20-3PM_Group007 
 * Rajeshwari Ramavath -700641464  
 * Jahnavi Kocharla-700646124
 * Bhanuteja Kuppili -700641597
 * 
 * This MultiThreadServer class is used to communicate with Client class for the calculation of interest rate.
 * This class receives annual interest rate, loan amount and number of years then using methods in 
 * Loan class calculation of monthly payment and total payment send back to Client class
 * 
 */
public class MultiThreadServer extends JFrame {
  // Text area for displaying contents
  private JTextArea jta = new JTextArea();

  public static void main(String[] args) {
	// calling constructor
    new MultiThreadServer();
  }

  public MultiThreadServer() {
    // Place text area on the frame
    setLayout(new BorderLayout());
    add(new JScrollPane(jta), BorderLayout.CENTER);
    setTitle("MultiThreadServer");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!

    try {
      // Create a server socket
      @SuppressWarnings("resource")
	ServerSocket serverSocket = new ServerSocket(5555);
      jta.append("MultiThreadServer started at " + new Date() + '\n');
      // Number a client
      int clientNo = 1;
      while (true) {
        // Listen for a new connection request
        Socket socket = serverSocket.accept();
        // Display the client number
        jta.append("Starting thread for client " + clientNo +
          " at " + new Date() + '\n');
        // Find the client's host name, and IP address
        InetAddress inetAddress = socket.getInetAddress();
        jta.append("Client " + clientNo + "'s host name is "
          + inetAddress.getHostName() + "\n");
        jta.append("Client " + clientNo + "'s IP Address is "
          + inetAddress.getHostAddress() + "\n");
        // Create a new thread for the connection
        HandleAClient task = new HandleAClient(socket);
        // Start the new thread
        new Thread(task).start();
        // Increment clientNo
        clientNo++;
      }
    }
    catch(IOException ex) {
      System.err.println(ex);
    }
  }

  // Inner class
  // Define the thread class for handling new connection
  class HandleAClient implements Runnable {
    private Socket socket; // A connected socket

    /** Construct a thread */
    public HandleAClient(Socket socket) {
      this.socket = socket;
    }

    /** Run a thread */
    public void run() {
    	   try {

    	        DataInputStream inputFromClient = new DataInputStream(
    	          socket.getInputStream());
    	        DataOutputStream outputToClient = new DataOutputStream(
    	          socket.getOutputStream());
    	        while (true) {
    	          double annualIntRate = inputFromClient.readDouble();
    	          int noOfYrs = inputFromClient.readInt();
    	          double loanAmt = inputFromClient.readDouble();
    	          Loan loan = new Loan(annualIntRate, noOfYrs, loanAmt);
    	          double monthlyPayment = loan.getMonthlyPayment();
    	          double totalPayment = loan.getTotalPayment();
    	          outputToClient.writeDouble(monthlyPayment);
    	          outputToClient.writeDouble(totalPayment);
    	          jta.append("Annual Interest Rate received from client: " +
    	        		  annualIntRate + '\n');
    	          jta.append("Number of Years received from client: " +
    	        		  noOfYrs + '\n');
    	          jta.append("Loan Amount received from client: " +
    	        		  loanAmt + '\n');
       	          jta.append("Monthly Payment calculated and sent is: " +
    	        		  monthlyPayment + '\n');
    	          jta.append("Total payment calculated and sent is: " +
    	        		  totalPayment + '\n');
    	        }
    	      }
    	      catch(IOException e) {
    	        System.err.println(e);
    	      }
    }
  }
}
