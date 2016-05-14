package clientServerNVN;


/*
*	PROJECT 1
*	Date: 02-09-2016
*	GROUP;A wed 12.20 to 3
*   NAVEEN KUMAR MORAMPUDI    700639817
*   MOUNIKA NARUKULLA         700648172
*   SWATHI SUDAGANI           700647043
*	Program: Client.java
*	Brief explanation: This is Client program connects to server *	and it accept the data inputs from multiple clients and  
*	communicate the data to the server.
*/


import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;


import javax.swing.*;

public class Client extends JFrame {
  // for receiving Annual Interest Rate
  private JTextField jtf1_lab1 = new JTextField();

  // for receiving Number of years
  private JTextField jtf2_lab1 = new JTextField();
  // for receiving Loan Amount
  private JTextField jtf3_lab1 = new JTextField();

  // area to display contents
  private JTextArea jta_lab1 = new JTextArea();

  private JButton jb_lab1= new JButton("Submit");
  
  
  // IO streams
  private DataOutputStream toServer_lab1;
  private DataInputStream fromServer_lab1;

  public static void main(String[] args) {
    new Client();
  }

  public Client() {
    // Panel x to hold the label and text field
    JPanel x_lab1 = new JPanel();
   
    
    //x.setLayout(new BorderLayout());
    x_lab1.setLayout(new GridBagLayout());
   // x.add(new JLabel("Annual Interest Rate"), FlowLayout.LEFT);
    //x.add(jtf, FlowLayout.CENTER);
    //jtf1.setHorizontalAlignment(JTextField.RIGHT);

    //x.add(new JLabel("Number of years"), FlowLayout.LEFT);
    //x.add(jtf2, FlowLayout.CENTER);
    //jtf2.setHorizontalAlignment(JTextField.RIGHT);
    
    GridBagConstraints z_lab1 = new GridBagConstraints();
    
    z_lab1.fill = GridBagConstraints.HORIZONTAL;
    
    z_lab1.gridx = 0;
    z_lab1.gridy = 0;
    x_lab1.add(new JLabel("Annual Interest Rate"),z_lab1);
    
    z_lab1.gridx = 1;
    z_lab1.gridy = 0;
    z_lab1.weightx = 0.5;
    x_lab1.add(jtf1_lab1,z_lab1);
     
    z_lab1.gridx = 2;
    z_lab1.gridy = 0;
    z_lab1.weightx = 0;
    z_lab1.ipady = 40;
    z_lab1.gridheight=3;
    x_lab1.add(jb_lab1,z_lab1);
    
    z_lab1.ipady = 0;
    z_lab1.gridx = 0;
    z_lab1.gridy = 1;
    z_lab1.gridheight=1;
    x_lab1.add(new JLabel("Number of years"),z_lab1);
    
    z_lab1.gridx = 1;
    z_lab1.gridy = 1;
    z_lab1.gridheight=1;
    x_lab1.add(jtf2_lab1,z_lab1);
   
    z_lab1.gridx = 0;
    z_lab1.gridy = 2;
    z_lab1.gridheight=1;
    x_lab1.add(new JLabel("Loan Amount"),z_lab1);
    
    z_lab1.gridx = 1;
    z_lab1.gridy = 2;
    z_lab1.gridheight=1;
    x_lab1.add(jtf3_lab1,z_lab1);
   
    
    setLayout(new BorderLayout());
    add(x_lab1, BorderLayout.NORTH);
    add(new JScrollPane(jta_lab1), BorderLayout.CENTER);

    jb_lab1.addActionListener(new ButtonListener()); // Register listener

    setTitle("Client");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!

    try {
      // Create a socket to connect to the server
      //Socket socket = new Socket("liang.armstrong.edu", 8000);
        Socket socket = new Socket("127.0.0.1", 8001);
      // Socket socket = new Socket("130.254.204.36", 8000);
      // Socket socket = new Socket("drake.Armstrong.edu", 8000);

      // Create an input stream to receive data from the server
        fromServer_lab1 = new DataInputStream(
        socket.getInputStream());

      // Create an output stream to send data to the server
      toServer_lab1 =
        new DataOutputStream(socket.getOutputStream());
    }
    catch (IOException ex) {
    	jta_lab1.append(ex.toString() + '\n');
    }
  }

  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        // Get the interest rate from the text field
        double interestRate= Double.parseDouble(jtf1_lab1.getText().trim());

        double years= Double.parseDouble(jtf2_lab1.getText().trim());
        
        double amount= Double.parseDouble(jtf3_lab1.getText().trim());
        
        // Send the radius to the server
        toServer_lab1.writeDouble(interestRate);
        toServer_lab1.writeDouble(years);
        toServer_lab1.writeDouble(amount);
        toServer_lab1.flush();

        // Get Monthly payment from the server
        double monthly= fromServer_lab1.readDouble();

        // Get total payment from the server
        double total = fromServer_lab1.readDouble();

        // Display to the text area
        jta_lab1.append("Annual Interest rate : " + interestRate+ '\t');
        jta_lab1.append("Number of Years : " + years+ '\t');
        jta_lab1.append("Loan Amount: " + amount + '\n');
        
        
        jta_lab1.append("Monthly Payment :"+ monthly + '\t');
        jta_lab1.append("Total Payment :"+ total + '\n');
      }
      catch (IOException ex) {
        System.err.println(ex);
      }
    }
  }
}

