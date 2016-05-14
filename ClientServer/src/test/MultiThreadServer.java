package test;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;

public class MultiThreadServer extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea jta=new JTextArea();
	

	public static void main(String args[])
	{
new MultiThreadServer();
	}

public MultiThreadServer()
{
	 setLayout(new BorderLayout());
	    add(new JScrollPane(jta), BorderLayout.CENTER);

	    setTitle("MultiThreadServer");
	    setSize(500, 300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	    try {
	        // Create a server socket
	        ServerSocket serverSocket = new ServerSocket(2222);
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
	          //serverSocket.close();
	        }
	        
	      }
	      catch(IOException ex) {
	        System.err.println(ex);
	      }
	    }

class HandleAClient implements Runnable {
    private Socket socket; // A connected socket

    /** Construct a thread */
    public HandleAClient(Socket socket) {
      this.socket = socket;
    }
    public void run() {
        try {
          // Create data input and output streams
          DataInputStream inputFromClient = new DataInputStream(
            socket.getInputStream());
          DataOutputStream outputToClient = new DataOutputStream(
            socket.getOutputStream());

          // Continuously serve the client
          while (true) {
            // Receive radius from the client
            double inrrate = inputFromClient.readDouble();
            double time = inputFromClient.readDouble();
            double amount = inputFromClient.readDouble();
            
            jta.append("Annual Interest Rate is : " + inrrate + '\n');
            
            jta.append("Number of Years is : " + time + '\n');
     
            jta.append("Loan Amount is : " + amount + '\n');
            // Compute area
            class Loan {
            	  private double annualInterestRate;
            	  private double numberOfYears;
            	  private double loanAmount;
            	 // private java.util.Date loanDate;

            	  /** Default constructor */
            	  public Loan()
            	  {
            		  this(inrrate,time,amount);
            	  }
            	   
            	  
            	  public Loan(double annualInterestRate, double numberOfYears,
            		      double loanAmount) {
            		    this.annualInterestRate = annualInterestRate;
            		    this.numberOfYears = numberOfYears;
            		    this.loanAmount = loanAmount;
            		   // loanDate = new java.util.Date();
            		  }
            	  public double getMonthlyPayment() {
            		    double monthlyInterestRate = annualInterestRate / 1200;
            		    double monthlyPayment = loanAmount * monthlyInterestRate / (1 -
            		      (Math.pow(1 / (1 + monthlyInterestRate), numberOfYears * 12)));
            		    return monthlyPayment;    
            		  }
            	  /** Find total payment */
            	  public double getTotalPayment() {
            	    double totalPayment = getMonthlyPayment() * numberOfYears * 12;
            	    return totalPayment;    
            	  }
            }
            Loan l=new Loan();
           double mp= l.getMonthlyPayment();
           double tp=l.getTotalPayment();
             // Send area back to the client
            outputToClient.writeDouble(mp);
            jta.append("monthly payment is : " + mp + '\n');
            outputToClient.writeDouble(tp);
            jta.append("total payment is : " + tp + '\n');
            
            
          }
        }
        catch(IOException e) {
          System.err.println(e);
        }
    }
}
}

      
    
  

    
