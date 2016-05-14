package clientServerJHNV;

//Client.java: The client sends the input to the server and receives
//result back from the server

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.ByteArrayOutputStream;	
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ClientUDP extends JFrame implements ActionListener{

    DatagramSocket Socket;

   //Text field for retriving annual interest rate, number of years, loan amount from UI
   private JTextField jtfAnnualInterestRate = new JTextField();
   private JTextField jtfNumOfYears = new JTextField();
   private JTextField jtfLoanAmount = new JTextField();
   private JButton jbtSubmit = new JButton("Submit");

   //Text area for displaying contents
   private JTextArea jta = new JTextArea();


   public static void main(String[] args){
        ClientUDP Client = new ClientUDP();
   }

public ClientUDP(){
 JPanel p1 = new JPanel();
 p1.setLayout(new GridLayout(3,1));
 p1.add(new JLabel("Annual Interest Rate"));
 p1.add(new JLabel("Number Of Years"));
 p1.add(new JLabel("Loan Amount"));

 Panel p2 = new Panel();
 p2.setLayout(new GridLayout(3,1));
 p2.add(jtfAnnualInterestRate);
 p2.add(jtfNumOfYears);
 p2.add(jtfLoanAmount);

 JPanel p = new JPanel();
 p.setLayout(new BorderLayout());
 p.add(p1, BorderLayout.WEST);

 p.add(p2,BorderLayout.CENTER);
 p.add(jbtSubmit,BorderLayout.EAST);

 jtfAnnualInterestRate.setHorizontalAlignment(JTextField.RIGHT);
 jtfNumOfYears.setHorizontalAlignment(JTextField.RIGHT);
 jtfLoanAmount.setHorizontalAlignment(JTextField.RIGHT);

 getContentPane().setLayout(new BorderLayout());
 getContentPane().add(p,BorderLayout.NORTH);
 getContentPane().add(new JScrollPane(jta),BorderLayout.CENTER);

 jbtSubmit.addActionListener(this); //Register listener

 setTitle("Client");
 setSize(500,300);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setVisible(true);

    }

public void actionPerformed(ActionEvent e){
   String actionCommand = e.getActionCommand();
   if(e.getSource() instanceof JButton){


     //Get the annual interest rate from the text field
          double annualInterestRate =
          Double.parseDouble(jtfAnnualInterestRate.getText().trim());

          //Get the number of years from the text field
          int numberOfYears =
          Integer.parseInt(jtfNumOfYears.getText());

          //Get the loan amount from the text field
          double loanAmount =
          Double.parseDouble(jtfLoanAmount.getText().trim());


         try {		
          Socket = new DatagramSocket();
          InetAddress IPAddress = InetAddress.getByName("localhost");
          byte[] incomingData = new byte[1024];
          Loan loan = new Loan(annualInterestRate,numberOfYears,loanAmount);	
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();	
          ObjectOutputStream os = new ObjectOutputStream(outputStream);	
          os.writeObject(loan);	
          byte[] data = outputStream.toByteArray();	
          DatagramPacket sendPacket = new DatagramPacket(data,data.length,IPAddress,9876);	
          Socket.send(sendPacket);	
          System.out.println("Message sent from client");	
          DatagramPacket incomingPacket = new DatagramPacket(incomingData,incomingData.length);
          Socket.receive(incomingPacket);
          String response = new String(incomingPacket.getData());	
          //System.out.println("Response from server:" + response);
          //Display to the text area
          jta.append(response +"\n");
          Thread.sleep(2000);
	
      } catch (UnknownHostException ec) {	
          ec.printStackTrace();	
      } catch (SocketException ec) {	
          ec.printStackTrace();	
      } catch (IOException ec) {	
          ec.printStackTrace();	
      } catch (InterruptedException ec) {	
          ec.printStackTrace();	
      }
         
   }
}
}