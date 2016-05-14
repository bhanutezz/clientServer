package clientServerNVN;

/*
*	PROJECT 1
*	Date: 02-09-2016
*	GROUP;A wed 12.20 to 3
*   NAVEEN KUMAR MORAMPUDI    700639817
*   MOUNIKA NARUKULLA         700648172
*   SWATHI SUDAGANI           700647043
*	Program: MultiThreadServer.java
*	Brief explanations: This Program helps in locating the
*	client with the help of client's host name, and IP address
*	and establishes a connection with client and sends desired 
*	data to the client and receives the data from the server.
*	In hand shake process takes place between each client and 
*	server.
*/

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;


public class MultiThreadServer extends JFrame {
  // Text area for displaying contents
  private JTextArea jta_lab1 = new JTextArea();

  public static void main(String[] args) {
    new MultiThreadServer();
  }

  public MultiThreadServer() {
    // Place text area on frame
    setLayout(new BorderLayout());
    add(new JScrollPane(jta_lab1), BorderLayout.CENTER);

    setTitle("MultiThreadServer");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!

    try {
      // Create a server socket
      ServerSocket serverSocket = new ServerSocket(8001);
      jta_lab1.append("MultiThreadServer started at " + new Date() + '\n');

      // Number a client
      int clientNo = 1;

      while (true) {
        // for a new connection request
        Socket socket = serverSocket.accept();

        // Display client number
        jta_lab1.append("Starting thread for client " + clientNo +
          " at " + new Date() + '\n');

        // Find client's host name, and IP address
        InetAddress inetAddress = socket.getInetAddress();
        jta_lab1.append("Client " + clientNo + "'s host name is "
          + inetAddress.getHostName() + "\n");
        jta_lab1.append("Client " + clientNo + "'s IP Address is "
          + inetAddress.getHostAddress() + "\n");

        // Create a new thread for connection
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

  
  // Define thread class for handling new connection
  class HandleAClient implements Runnable {
    private Socket socket; // A connected socket

    /** Construct a thread */
    public HandleAClient(Socket socket) {
      this.socket = socket;
    }

    /** Run a thread */
    public void run() {
      try {
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
          socket.getOutputStream());

        // Continuously serve client
        while (true) {
          // Receive radius from client
          double interestRate = inputFromClient.readDouble();
          
          int years= inputFromClient.readInt();
          
          double amount= inputFromClient.readDouble();
          
          
          Loan loan=new Loan(interestRate,years,amount);
          
         // monthly payment
          double monthly= loan.getMonthlyPayment();
          
          // total payment
          double total= loan.getTotalPayment();

          // Send area back to client
          outputToClient.writeDouble(monthly);
          outputToClient.writeDouble(total);
          
          jta_lab1.append("Annual Interest rate : " + interestRate+ '\t');
          jta_lab1.append("Number of Years : " + years+ '\t');
          jta_lab1.append("Loan Amount: " + amount + '\n');
          
          jta_lab1.append("Monthly Payment : " + monthly + '\t');
          jta_lab1.append("Total Payment : " + total + '\n');
        }
      }
      catch(IOException e) {
        System.err.println(e);
      }
    }
  }
}

